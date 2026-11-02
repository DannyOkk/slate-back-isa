# Monitoreo y observabilidad

## Actuator

El backend expone los endpoints operativos necesarios:

| Endpoint | Acceso | Uso |
|---|---|---|
| `/actuator/health` | Público | Probes de liveness/readiness y estado de PostgreSQL |
| `/actuator/health/liveness` | Público | Detectar si el proceso está vivo |
| `/actuator/health/readiness` | Público | Detectar si puede recibir tráfico |
| `/actuator/info` | Público | Información no sensible de la aplicación |
| `/actuator/metrics` | JWT | Catálogo de métricas |
| `/actuator/metrics/{name}` | JWT | Valor de una métrica concreta |

El endpoint de métricas permanece protegido por Spring Security. `info.env` está
deshabilitado para no exponer variables de entorno ni configuración sensible.

Ejemplos:

```powershell
Invoke-RestMethod `
  -Uri "http://localhost:8080/actuator/health" `
  -Method Get

Invoke-RestMethod `
  -Uri "http://localhost:8080/actuator/health/readiness" `
  -Method Get

$headers = @{ Authorization = "Bearer <jwt>" }
Invoke-RestMethod `
  -Uri "http://localhost:8080/actuator/metrics" `
  -Headers $headers `
  -Method Get
```

## Métricas disponibles

Actuator registra Micrometer automáticamente. Las métricas relevantes incluyen:

- `hikaricp.connections.active`
- `hikaricp.connections.idle`
- `hikaricp.connections.pending`
- `hikaricp.connections.max`
- `hikaricp.connections.min`
- `jdbc.connections.active`
- `http.server.requests`
- `jvm.memory.used`
- `jvm.gc.pause`
- `process.cpu.usage`
- `system.cpu.usage`

El pool se identifica como `SlateHikariPool`. En producción se mantiene
`maximum-pool-size=5` para no agotar las conexiones disponibles de PostgreSQL
Flexible Server B1ms. Las métricas incluyen el tag `application=slate-backend`.

Para consultar una métrica:

```powershell
Invoke-RestMethod `
  -Uri "https://<fqdn>/actuator/metrics/hikaricp.connections.active" `
  -Headers $headers `
  -Method Get
```

## Azure Container Apps

En Container Apps:

1. Abrir `slate-back-isa`.
2. Ir a **Monitoring → Metrics**.
3. Seleccionar métricas de réplicas, CPU, memoria y requests.
4. Para logs, usar **Monitoring → Log stream** o **Monitoring → Logs**.

La extensión CLI instalada presentó el error `eventStreamEndpoint` al ejecutar
`az containerapp logs show` en el entorno Express. Es una limitación del
entorno/versión de la herramienta, no un fallo del backend; el Portal es la
alternativa operativa documentada.

No se crea un workspace nuevo. Si el entorno ya envía datos a Log Analytics,
se debe consultar uno de los workspaces existentes y evitar duplicar ingestión.

## Log Analytics y costos

La ingestión y retención de logs puede generar costos. Para el crédito de
Azure for Students:

- conservar un único workspace elegido;
- mantener la retención mínima requerida para la defensa;
- evitar logs DEBUG en producción;
- no habilitar diagnósticos duplicados;
- revisar **Cost Management → Cost analysis** después de pruebas;
- escalar Container Apps a cero y detener PostgreSQL cuando no se use.

No se crean Application Insights, alertas ni workspaces nuevos en esta fase.

## Factores 12-factor

- **III Config:** secretos y destinos se inyectan por variables de entorno.
- **V Build, release, run:** la imagen versionada y el workflow separan build,
  publicación y ejecución.
- **VII Port binding:** el puerto se configura mediante `PORT`, con 8080 como
  valor por defecto.
- **XI Logs:** la aplicación escribe logs a stdout/stderr para que Container
  Apps y Log Analytics puedan recolectarlos.

## Operación segura

Antes de activar recursos para una prueba:

```powershell
az postgres flexible-server show `
  --name slate-db-isa `
  --resource-group universidad `
  --query "{state:state}" `
  --output json

az containerapp show `
  --name slate-back-isa `
  --resource-group universidad `
  --query "{status:properties.runningStatus,minReplicas:properties.template.scale.minReplicas}" `
  --output json
```

Al finalizar:

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
