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

### Publicación manual

La publicación solo está disponible mediante `workflow_dispatch`. En GitHub Actions se debe ejecutar el workflow y activar el input **Publish the tested image to ACR**. El job espera a que CI finalice correctamente y publica:

```text
acringenieria.azurecr.io/slate-backend:<commit-sha>
```

La etiqueta usa el SHA completo del commit y es inmutable por convención operativa. El workflow no ejecuta `az containerapp update`, no inicia PostgreSQL y no cambia réplicas; por lo tanto, la publicación no enciende recursos de cómputo ni despliega automáticamente la aplicación.

### Costos y límites

- Publicar una imagen agrega almacenamiento marginal al ACR existente.
- No se crea un ACR nuevo.
- No se modifica el repositorio `goat-grup-one`.
- El Service Principal solo necesita `AcrPush` para este job.
- El environment protegido opcional `azure-acr` puede requerir aprobación antes de publicar.
- `AZURE_RESOURCE_GROUP` se conserva como secreto para futuras operaciones, pero esta versión no lo usa porque no despliega Container Apps.

### Validación local

La implementación fue validada sin ejecutar GitHub Actions ni modificar Azure:

- `.\mvnw.cmd --batch-mode test`: 50 tests exitosos.
- Build Docker local `linux/amd64`: exitoso.
- `git diff --check`: sin errores.
- `actionlint` no estaba instalado en el host; la estructura del workflow fue revisada mediante el diff.
