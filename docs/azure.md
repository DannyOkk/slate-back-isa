# Fase 7: Preparación de Microsoft Azure

## Regla de seguridad y costos

Esta fase fue ejecutada en modo **solo lectura**. No se crearon, modificaron ni eliminaron recursos, proveedores, roles, Service Principals o secretos. Los comandos de esta guía usan Azure CLI desde PowerShell 5.1 y deben ejecutarse después de confirmar la suscripción activa.

Antes de crear cualquier recurso:

1. Confirmar suscripción, crédito restante y fecha de vencimiento en **Cost Management + Billing** del portal.
2. Confirmar región y disponibilidad del SKU elegido.
3. Calcular el costo mensual y el costo del período de prueba.
4. Definir etiquetas, presupuesto y alerta de gasto.
5. Obtener autorización explícita para cada creación.

## Resultado de la auditoría del 01/11/2026

| Elemento | Resultado | Decisión |
|---|---|---|
| Suscripción activa | `Azure for Students`, habilitada | Usar solo esta suscripción; no cambiarla automáticamente |
| Resource Group | `universidad`, `eastus` | Reutilizable, sin modificarlo durante esta fase |
| ACR | `acringenieria.azurecr.io`, Standard; `adminUserEnabled=true` | Reutilizable si se confirma autorización y separación por tags/repositorio; deshabilitar admin user antes de producción |
| Repositorio existente | `goat-grup-one` | No modificarlo; Slate debe usar un repositorio/tag propio |
| Container Apps Environment | `goat-grup-one-env`, Consumption, East US | Potencialmente reutilizable para una app adicional, sujeto a aislamiento y autorización |
| Container App existente | `goat-grup-one` | No modificarlo |
| Log Analytics | Cuatro workspaces `PerGB2018`, 30 días | Hay duplicación; no crear otro sin elegir uno conscientemente |
| PostgreSQL Flexible Server | No existe en `universidad` | No crear todavía |
| `Microsoft.DBforPostgreSQL` | `NotRegistered` | Registrar solo después de aprobar región, SKU y costo |
| ACR/App/Log Analytics providers | Registrados | No requieren acción ahora |
| RBAC | Usuario con Owner a nivel suscripción; también User Access Administrator; hay un Contributor Service Principal en el Resource Group | Permisos suficientes para evaluar creación, pero no se crearán identidades ni roles ahora |
| Management Groups | Lectura no autorizada | No es bloqueante para el alcance de esta fase |
| Budget | No hay presupuesto visible en el Resource Group | Crear un presupuesto sería una modificación; queda pendiente de autorización |

La cuenta, tenant, identificadores y nombres sensibles obtenidos por CLI no se guardan en este repositorio.

## Arquitectura recomendada pendiente de autorización

1. Reutilizar `universidad`.
2. Reutilizar `acringenieria` y publicar Slate con un repositorio/tag propio, por ejemplo `slate-backend:<sha>`. No reutilizar ni sobrescribir `goat-grup-one`.
3. Reutilizar `goat-grup-one-env` únicamente si se confirma que el entorno compartido no es un problema para la defensa y se crea una Container App independiente.
4. Crear un único PostgreSQL Flexible Server para Slate solo después de confirmar disponibilidad de SKU, región, almacenamiento, backups y costo. La aplicación usaría `sslmode=require`, `DDL_AUTO=validate` y secretos fuera de la imagen.
5. Elegir un solo workspace Log Analytics existente para evitar duplicación de costos y no modificar los demás.

La alternativa de ejecutar PostgreSQL dentro de Container Apps no es recomendable para producción: el almacenamiento efímero no reemplaza una base administrada con persistencia, backups y disponibilidad.

## Estimación de costos

Los valores son orientativos y deben confirmarse en [Azure Pricing Calculator](https://azure.microsoft.com/pricing/calculator/) antes de crear recursos:

| Recurso | Configuración | Estimación de referencia |
|---|---|---:|
| ACR existente | Standard | Aproximadamente USD 5/mes; reutilizarlo evita otro registry |
| Container Apps | Consumption, `minReplicas=0` | Puede quedar dentro de las cuotas gratuitas; depende de vCPU-segundos, GiB-segundos, requests y logs |
| PostgreSQL Flexible Server | Standard_B1ms | Aproximadamente USD 15/mes de cómputo según referencias públicas; almacenamiento, backups y región pueden sumar |
| Log Analytics | PerGB2018 | Depende de ingestión y retención; mantener logs mínimos y 30 días |

Estrategia de crédito: detener PostgreSQL cuando no se use, mantener Container Apps con `minReplicas=0`, evitar múltiples ACR/workspaces y eliminar imágenes antiguas. No asumir que detener todos los recursos deja costo cero: almacenamiento, backups, ACR y logs pueden seguir facturando.

## Comandos PowerShell de auditoría

```powershell
az account show --only-show-errors --output json
az group list --only-show-errors --output table
az resource list --resource-group universidad --only-show-errors --output table
az acr list --resource-group universidad --only-show-errors --output table
az containerapp env list --resource-group universidad --only-show-errors --output table
az containerapp list --resource-group universidad --only-show-errors --output table
az postgres flexible-server list --resource-group universidad --only-show-errors --output table
az monitor log-analytics workspace list --resource-group universidad --only-show-errors --output table
az consumption budget list --resource-group universidad --only-show-errors --output table
```

Para comprobar permisos sin cambiar nada:

```powershell
az role assignment list `
  --scope "/subscriptions/<SUBSCRIPTION_ID>/resourceGroups/universidad" `
  --include-inherited `
  --output table
```

## Próxima autorización necesaria

La Fase 8 no debe comenzar creando secretos automáticamente. Antes habrá que decidir:

- si se reutiliza el ACR y el Container Apps Environment existentes;
- qué región y SKU PostgreSQL están realmente disponibles;
- qué workspace de Log Analytics se conservará;
- qué presupuesto y alerta se configurarán;
- si se permite registrar `Microsoft.DBforPostgreSQL`;
- y qué permisos mínimos tendrá el futuro Service Principal.

## Fase 9: publicación manual — preparación y costos

La preparación local se completó sin modificar Azure:

- `.\mvnw.cmd test`: 50 tests, 0 fallos.
- Imagen construida para `linux/amd64`.
- Tag inmutable local: `slate-backend:e50018636cdc`.
- Usuario runtime: `spring:spring`.
- El `.env` no está rastreado y las variables sensibles no están embebidas en la imagen.
- No se hizo `docker push`.

### Operaciones bloqueadas hasta autorización

| Operación | Motivo | Estado |
|---|---|---|
| `docker push` al ACR | Puede aumentar almacenamiento y egreso; el ACR Standard ya genera costo fijo | Bloqueada |
| Registrar `Microsoft.DBforPostgreSQL` | Cambio de configuración de suscripción; paso previo a crear el servidor | Bloqueada |
| Crear PostgreSQL Flexible Server | Cómputo, almacenamiento, backups y logs facturables | Bloqueada |
| Asignar `Container Apps Contributor` | Cambio RBAC; necesario para desplegar una app propia | Bloqueada |
| Crear Container App Slate | Puede generar consumo de vCPU, memoria, requests y logs | Bloqueada |

### Estimación previa obligatoria

La referencia disponible para East US es:

- ACR Standard existente: aproximadamente USD 5/mes; una imagen adicional normalmente suma almacenamiento/egreso marginal, pero no es costo cero garantizado.
- Container Apps Consumption: tiene cuota gratuita mensual, pero el exceso se factura por vCPU-segundos, GiB-segundos, requests y logs. Se debe configurar `minReplicas=0`.
- PostgreSQL Flexible Server `Standard_B1ms`: referencia aproximada de USD 15/mes de cómputo, más almacenamiento, backups y posibles logs. El costo exacto depende de región y oferta vigente.

Con crédito de USD 100, una combinación ACR + PostgreSQL durante un mes completo puede consumir aproximadamente USD 20 o más antes de almacenamiento/logs. La estrategia segura es desplegar solo después de confirmar el precio en Azure Pricing Calculator, usar el período mínimo de prueba y detener PostgreSQL cuando no se use. Detenerlo no elimina necesariamente costos de almacenamiento, backups, ACR o Log Analytics.

La autorización siguiente debe indicar explícitamente si se permite cada operación y el presupuesto máximo aceptado. Hasta entonces, Slate permanece local.

## Riesgos detectados para la siguiente fase

- El ACR existente tiene habilitado el usuario administrador. No se usará esa credencial para CI/CD; se debe evaluar identidad administrada o Service Principal con rol `AcrPush` limitado.
- Existe un Service Principal con `Contributor` sobre el Resource Group. No se asumirá que cumple mínimo privilegio para Slate ni se modificarán sus permisos sin autorización.
- El nombre del Resource Group y el entorno contienen el proyecto original. La futura Container App de Slate debe tener nombre, imagen, secretos y tags propios para evitar cualquier modificación accidental.

## Fase 8: identidad de CI/CD creada

Se creó un Service Principal nuevo y exclusivo para Slate con nombre `slate-back-isa-ci`. No se reutilizó el Service Principal existente con `Contributor`. La operación de identidad y RBAC no genera un cargo directo, pero la publicación o ejecución de recursos Azure sí puede generar costos en fases posteriores.

Asignaciones verificadas:

| Rol | Alcance | Estado |
|---|---|---|
| `Reader` | Resource Group `universidad` | Asignado |
| `AcrPush` | Registry `acringenieria` | Asignado |
| `Container Apps Contributor` | Futura Container App Slate | Pendiente hasta que exista la app |

El `clientSecret` fue regenerado y guardado temporalmente fuera del repositorio en la máquina local. Debe copiarse manualmente a GitHub como `AZURE_CLIENT_SECRET` y eliminarse inmediatamente del archivo temporal. No se debe pegar en el repositorio, issues, commits, chat ni logs.

### GitHub Secrets requeridos

En GitHub: **Settings → Secrets and variables → Actions → New repository secret**.

Crear estos secretos:

| Secret | Valor |
|---|---|
| `AZURE_CLIENT_ID` | App ID del Service Principal `slate-back-isa-ci` |
| `AZURE_CLIENT_SECRET` | `clientSecret` del archivo temporal, solo durante la carga |
| `AZURE_TENANT_ID` | Tenant ID de la suscripción activa |
| `AZURE_SUBSCRIPTION_ID` | Subscription ID de la suscripción activa |
| `AZURE_RESOURCE_GROUP` | `universidad` |
| `AZURE_ACR_NAME` | `acringenieria` |

GitHub no permite volver a leer el valor de un secret después de guardarlo. Si se pierde, se debe rotar con `az ad sp credential reset`; nunca imprimir credenciales en workflows.

Para comprobar localmente que el archivo temporal existe sin mostrar su contenido:

```powershell
Test-Path "$env:TEMP\slate-back-isa-github-credentials.json"
Remove-Item -LiteralPath "$env:TEMP\slate-back-isa-github-credentials.json" -Force
```

La creación de `Container Apps Contributor` queda deliberadamente pendiente. Primero debe existir una Container App propia de Slate; no se asignará permiso de despliegue sobre `goat-grup-one`.

## Fase 9: bloqueo de aprovisionamiento en Azure for Students

Se calculó el precio antes de crear recursos. Para `westcentralus`, la API pública de precios devolvió aproximadamente USD 0,0204/h para `Standard_B1ms` y USD 0,138/GB-mes para almacenamiento; con 32 GB, el total estimado de cómputo y almacenamiento es de aproximadamente USD 19,32/mes, antes de backups y logs. El ACR Standard existente mantiene su costo base aproximado de USD 5/mes.

La ejecución autorizada no pudo continuar por restricciones de la suscripción:

1. `eastus`: el comando de creación devolvió `The location is restricted from performing this operation`.
2. `westcentralus`: el comando de creación devolvió `RequestDisallowedByAzure`, indicando que la policy de Azure for Students solo permite un conjunto de regiones elegibles para la suscripción.

La verificación posterior confirmó que no existen `slate-db-isa`, `slate-back-isa` ni el repositorio `slate-backend` en ACR. No se asignó `Container Apps Contributor`, no se hizo push y no se modificó `goat-grup-one`. Para continuar se necesita una de estas decisiones: solicitar a Azure soporte/excepción de región, elegir una región permitida que también ofrezca PostgreSQL Flexible Server, o aprobar explícitamente una arquitectura alternativa con otro servicio de base de datos y su costo.

### Actualización posterior: PostgreSQL creado e imagen publicada

El servidor PostgreSQL fue creado manualmente en `Canada Central` con un costo mostrado por el portal de aproximadamente USD 17/mes. La imagen local se publicó sin modificar el repositorio original:

```text
acringenieria.azurecr.io/slate-backend:e50018636cdc
sha256:a711502e85aeb6b3c474413bf1941e9fc35c0eb30f4fdddef7618b9e03c3d955
```

La Container App `slate-back-isa` ya fue creada en el entorno `slate-isa`, usando ese repositorio/tag, la base `slate_db`, `sslmode=require` y secretos de base/JWT. La URL pública es `https://slate-back-isa.calmriver-a6a4e760.canadacentral.azurecontainerapps.io`.

La verificación devolvió:

- `/actuator/health`: `UP`.
- Componente `db`: `PostgreSQL`, `UP`.
- Readiness/liveness: `UP`.
- La raíz `/` devuelve `403` porque no existe una ruta pública para `/`.
- El login con el usuario de prueba local devolvió `401`; no se creó automáticamente ningún usuario cloud.

El siguiente paso es registrar un usuario cloud mediante `/api/auth/register`, validar JWT y luego cambiar `DDL_AUTO` de `update` a `validate` antes de apagar el cómputo.

La validación se completó correctamente:

- Registro del usuario cloud: exitoso.
- Login y emisión de JWT: exitosos.
- `/api/auth/validate`: devolvió el perfil esperado.
- `DDL_AUTO` quedó cambiado a `validate`.
- `slate-back-isa` quedó con `minReplicas=0`.
- `slate-db-isa` quedó en estado `Stopped`.

El usuario de prueba se creó en la base cloud. La contraseña no se documenta ni se almacena en el repositorio.

## Fase 12: gestión operativa de Container Apps

La Container App `slate-back-isa` se opera separadamente de `goat-grup-one`.
Antes de cada operación que pueda activar cómputo hay que revisar el crédito y
aceptar el costo temporal. PostgreSQL debe estar `Ready` antes de activar la
aplicación.

### Consultar estado

```powershell
az containerapp show `
  --name slate-back-isa `
  --resource-group universidad `
  --query "{name:name,status:properties.runningStatus,minReplicas:properties.template.scale.minReplicas,image:properties.template.containers[0].image}" `
  --output json

az containerapp revision list `
  --name slate-back-isa `
  --resource-group universidad `
  --query "[].{name:name,active:properties.active,health:properties.healthState,traffic:properties.trafficWeight}" `
  --output table

az postgres flexible-server show `
  --name slate-db-isa `
  --resource-group universidad `
  --query "{name:name,state:state}" `
  --output json
```

### Iniciar y detener

El script `script_az.py` intenta primero `az containerapp start`. Si la CLI
instalada no incluye ese comando, usa la acción REST equivalente mediante
`az rest`. Para activar la aplicación manualmente desde PowerShell:

```powershell
az containerapp update `
  --name slate-back-isa `
  --resource-group universidad `
  --min-replicas 1 `
  --max-replicas 1 `
  --only-show-errors
```

Si la aplicación fue detenida administrativamente y continúa en `Stopped`, se
puede crear/reactivar la revision con la imagen actual:

```powershell
az containerapp update `
  --name slate-back-isa `
  --resource-group universidad `
  --image acringenieria.azurecr.io/slate-backend:e50018636cdc `
  --min-replicas 1 `
  --max-replicas 1 `
  --only-show-errors
```

Para apagar sin borrar:

```powershell
az containerapp update `
  --name slate-back-isa `
  --resource-group universidad `
  --min-replicas 0 `
  --max-replicas 1 `
  --only-show-errors

az postgres flexible-server stop `
  --name slate-db-isa `
  --resource-group universidad `
  --only-show-errors
```

El script también usa la acción REST `stop` cuando la CLI no trae
`az containerapp stop`. Como alternativa visual se puede usar el botón
**Stop/Start** del Portal. No se debe confundir escalar a cero con eliminar la
aplicación.

### Reiniciar

En el script: **Ciclo de vida → Reiniciar servicio**. Primero intenta
`az containerapp revision restart`; si el entorno devuelve error, fuerza una
nueva revisión usando la misma imagen. En el Portal: **Container App →
Revisions → seleccionar revision activa → Restart**.

La operación CLI puede devolver un error interno en el entorno Express; el
fallback del script ejecuta `az containerapp update` con la misma imagen. En
ambos casos hay que comprobar la revisión Healthy y `/actuator/health`.

### Health y logs

```powershell
Invoke-RestMethod `
  -Uri "https://<fqdn>/actuator/health" `
  -Method Get
```

Si `az containerapp logs show` falla con `eventStreamEndpoint` por una
limitación de la extensión/entorno Express, el script informa el error y se
debe usar el Portal:

**Container App → Monitoring → Log stream** o **Monitoring → Logs**.

Si Log Analytics está conectado, consultar la tabla de logs del entorno desde
**Log Analytics workspace → Logs**. No crear un workspace adicional.

### Actualizar imagen y rollback

Ambas operaciones crean una nueva revision y deben usar tags SHA existentes:

```powershell
az containerapp update `
  --name slate-back-isa `
  --resource-group universidad `
  --image acringenieria.azurecr.io/slate-backend:<commit-sha> `
  --min-replicas 1 `
  --max-replicas 1 `
  --only-show-errors
```

Para rollback, reemplazar `<commit-sha>` por un tag anterior validado. Antes hay
que comprobar el digest en ACR, compatibilidad del esquema PostgreSQL y el costo
de reactivar los recursos. No usar `latest`.

### Eliminación

La eliminación es destructiva y no forma parte de la rutina normal:

```powershell
az containerapp delete `
  --name slate-back-isa `
  --resource-group universidad `
  --yes
```

No ejecutar este comando sin autorización explícita y sin confirmar la
retención requerida de datos y configuración.

### Resultado de la prueba de Fase 12

- PostgreSQL se inició temporalmente y llegó a `Ready`.
- Container App quedó `Running`.
- Revision `slate-back-isa--latest`: `Healthy`, tráfico 100%.
- `/actuator/health`: `UP`; componente `db`: `UP`.
- La reactivación mediante `update` con la misma imagen fue exitosa.
- El script incluye acciones de start/stop por CLI o REST, restart con fallback,
  listado de revisiones, rollback y eliminación protegida por confirmación.
- `az containerapp revision restart` y `az containerapp logs show` pueden
  presentar limitaciones del entorno Express; quedaron documentadas sus
  alternativas.
- La app queda encendida temporalmente para que el usuario la apague desde el
  Portal al finalizar la revisión. PostgreSQL no debe quedar encendido.
