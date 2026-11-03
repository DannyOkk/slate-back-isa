# CI/CD: identidad y secretos Azure

## Service Principal de Slate

El pipeline futuro usará el Service Principal `slate-back-isa-ci`. Tiene únicamente:

- `Reader` sobre el Resource Group `universidad`.
- `AcrPush` sobre `acringenieria`.
- Sin `Owner`, `User Access Administrator` ni `Contributor` de suscripción.
- Sin permiso de Container Apps hasta que se cree la Container App propia de Slate.

No usar el Service Principal existente con `Contributor`, porque sus permisos son más amplios y su propósito pertenece a la infraestructura previa.

## Configuración manual de GitHub

En el repositorio: **Settings → Secrets and variables → Actions → New repository secret**.

Agregar:

```text
AZURE_CLIENT_ID
AZURE_CLIENT_SECRET
AZURE_TENANT_ID
AZURE_SUBSCRIPTION_ID
AZURE_RESOURCE_GROUP
AZURE_ACR_NAME
```

Usar los valores del archivo temporal generado localmente y de `az account show`. No copiar valores reales a este archivo, a `.env.example`, al workflow, a issues o a commits.

Después de guardar `AZURE_CLIENT_SECRET`, eliminar el archivo temporal:

```powershell
Remove-Item -LiteralPath "$env:TEMP\slate-back-isa-github-credentials.json" -Force
```

## Rotación

Si el secreto se expone o se pierde, rotarlo y no editar el historial Git:

```powershell
az ad sp credential reset `
  --id "<AZURE_CLIENT_ID>" `
  --years 1 `
  --only-show-errors `
  --output json
```

La salida debe redirigirse a un archivo temporal fuera del repositorio. Copiar el nuevo secreto a GitHub y eliminar ese archivo.

## Nota de seguridad

La alternativa preferida para un pipeline maduro es OpenID Connect (OIDC) con credenciales federadas, porque evita guardar `AZURE_CLIENT_SECRET`. Se mantiene el secreto en esta fase porque es el flujo académico solicitado; antes de producción se debe evaluar la migración a OIDC.

## Fase 10: workflow automatizado

El workflow [`ci-cd.yml`](../.github/workflows/ci-cd.yml) separa validación y publicación.

### CI automático

Se ejecuta en cada pull request y en cada push a `main`. Ejecuta:

1. Java 21 y caché Maven.
2. `./mvnw --batch-mode test`.
3. Build Docker para `linux/amd64`.
4. Escaneo Grype con severidad mínima `high`; vulnerabilidades altas o críticas hacen fallar el job.

### Publicación y despliegue condicionado

En cada `push` a `main`, el job `ci` valida y el job `publish` publica la misma
imagen escaneada en ACR. En un `pull_request`, solo se ejecuta la validación.
También se puede ejecutar el workflow con `workflow_dispatch`; el input
`deploy` controla si se solicita CD.

```text
acringenieria.azurecr.io/slate-backend:<commit-sha>
```

La etiqueta usa el SHA completo del commit. El job `ci` exporta la imagen
construida y escaneada como artefacto; `publish` la carga y la sube sin
reconstruirla. Así, la imagen publicada es exactamente la que pasó tests,
Docker y Grype.

El job `deploy` depende de `ci` y `publish` y ejecuta `az containerapp update`
solo en estos casos:

- `push` a `main` cuyo mensaje contiene `[deploy]`.
- `workflow_dispatch` con `deploy=true`.

Después comprueba que Container Apps use el mismo tag SHA y consulta
`/actuator/health`. Un push normal sin `[deploy]` publica, pero no actualiza la
Container App.

### Costos y límites

- Publicar una imagen agrega almacenamiento marginal al ACR existente.
- No se crea un ACR nuevo.
- No se modifica el repositorio `goat-grup-one`.
- El Service Principal necesita `AcrPush` para publicación y permiso de
  actualización de la Container App para CD.
- Los environments `azure-acr` y `azure-deploy` pueden requerir aprobación
  antes de cada operación.
- `AZURE_RESOURCE_GROUP` y `AZURE_ACR_NAME` se usan en el deploy para evitar
  nombres de infraestructura hardcodeados en el workflow.

### Validación local

La implementación local fue validada con:

- `.\mvnw.cmd --batch-mode test`: 52 tests exitosos.
- Compilación del script: `python -m py_compile .\script_az.py`.
- `git diff --check`: sin errores.
- `actionlint` no está instalado en el host; el YAML fue revisado
  estructuralmente. La validación definitiva del workflow se hará en GitHub.

La ejecución real del workflow CodeQL en GitHub fue verificada en el run
[`41492423754`](https://github.com/DannyOkk/slate-back-isa/actions/runs/41492423754):
el job `Analyze Java` terminó correctamente, incluyendo inicialización,
compilación y análisis CodeQL. La API disponible no permite leer los nombres de
secrets del repositorio; el usuario confirmó que los secrets requeridos fueron
creados. La publicación y el deploy deben verificarse ejecutando los casos A, B
y C en GitHub Actions.

## Fase 14: CodeQL y revisión de seguridad

El workflow [`codeql.yml`](../.github/workflows/codeql.yml) ejecuta análisis SAST
para Java 21 en pull requests, pushes a `main` y mediante `workflow_dispatch`.
Usa Maven en modo manual para que CodeQL observe la compilación real del
proyecto y publica los resultados en GitHub Code Scanning con permisos mínimos:

- `contents: read`
- `actions: read`
- `security-events: write`

La revisión del código confirmó que:

- Los repositorios usan métodos derivados de Spring Data; no hay SQL o JPQL
  construido dinámicamente.
- Los cuerpos de los endpoints usan `@Valid` y los DTOs principales tienen
  restricciones de Bean Validation.
- Las rutas protegidas requieren autenticación y el endpoint de métricas no es
  público.
- El workflow de CodeQL no publica imágenes, no accede a Azure y no inicia
  PostgreSQL ni Container Apps.

CodeQL cubre el código fuente Java. Maven y Grype continúan cubriendo
dependencias y vulnerabilidades de la imagen.

## Fase 11: versionado y condiciones de despliegue

### Tres escenarios

| Escenario | Evento | Publica en ACR | Enciende Azure |
|---|---|---:|---:|
| Caso A: CI sin deploy | `push` a `main` sin `[deploy]` | Sí | No |
| Caso B: CI + CD | `push` a `main` con `[deploy]` | Sí | Sí |
| Caso C: ejecución manual | `workflow_dispatch` con `deploy=true` | Sí | Sí |

Un `pull_request` solo ejecuta CI. Publicar una imagen en ACR no inicia
PostgreSQL; el deploy solo actualiza la Container App y requiere que la base
esté disponible para que el health check sea exitoso.

### Tags y digest

Cada imagen usa el SHA completo del commit:

```text
acringenieria.azurecr.io/slate-backend:<40-character-commit-sha>
```

El SHA permite identificar el código fuente exacto. El digest SHA-256 devuelto
por ACR identifica el contenido exacto de la imagen. Ambos valores deben
conservarse al registrar una publicación. No se usa `latest` como referencia de
despliegue porque es mutable y puede ocultar qué versión está activa.

Para consultar un digest publicado:

```powershell
az acr repository show `
  --name acringenieria `
  --image slate-backend:<commit-sha> `
  --query manifestDigest `
  --output tsv
```

### Rollback

El rollback consiste en seleccionar un tag SHA anterior que ya haya sido
validado y actualizar la Container App para referenciarlo. Puede hacerse con
`script_az.py` o con el comando manual documentado; el workflow CD normal
despliega el SHA del commit actual y no recibe un tag arbitrario.

Antes de cualquier rollback hay que comprobar:

1. El tag existe en ACR.
2. El digest corresponde a la versión aprobada.
3. La configuración de base de datos es compatible.
4. Se acepta temporalmente el costo de encender los recursos.

### Protección del environment

Se recomienda crear los environments `azure-acr` y `azure-deploy` en
**Settings → Environments** y configurar revisores obligatorios. Una aprobación
puede bloquear la publicación o el deploy antes de tocar Azure.
