# Docker Compose local

## Alcance

El stack local contiene dos servicios:

- `db`: PostgreSQL 16 Alpine, accesible desde el backend por `db:5432`.
- `backend`: imagen multietapa construida desde el `Dockerfile`, expuesta en `http://localhost:8080`.

Los servicios comparten la red `slate-network`. PostgreSQL usa el volumen nombrado `slate-postgres-data`, por lo que los datos sobreviven a la eliminación de contenedores.

## Preparación

Copiá la plantilla y editá los valores locales en PowerShell:

```powershell
Copy-Item .env.example .env
notepad .env
```

No agregues `.env` al repositorio. `DB_PASSWORD` y `JWT_SECRET` son obligatorios para Compose y deben ser valores locales.

No es necesario iniciar el PostgreSQL instalado en Windows. Compose administra su propio servidor.

## Iniciar y verificar

Validá primero la interpolación de variables:

```powershell
docker compose config
```

Construí y levantá los servicios en segundo plano:

```powershell
docker compose up --build -d
docker compose ps
```

El backend espera a que `pg_isready` informe saludable en `db`. Su health check consulta `/actuator/health` mediante el `wget` incluido en Alpine BusyBox, sin agregar paquetes runtime adicionales.

Verificá la aplicación:

```powershell
Invoke-RestMethod http://localhost:8080/actuator/health
Invoke-RestMethod http://localhost:8080/actuator/info
```

La respuesta esperada del primer endpoint contiene `status` igual a `UP`.

## Logs y reconstrucción

```powershell
docker compose logs -f backend
docker compose logs -f db
docker compose up --build -d
```

Para salir del seguimiento de logs, presioná `Ctrl+C`; los servicios continúan ejecutándose porque fueron iniciados con `-d`.

## Detener y limpiar

```powershell
# Detiene y elimina contenedores y red; conserva los datos de PostgreSQL.
docker compose down

# Detiene y elimina también el volumen; borra todos los datos locales.
docker compose down -v
```

Usá `down -v` solo cuando quieras reinicializar completamente la base. No se debe ejecutar en un entorno con datos que se necesiten conservar.

## Variables relevantes

| Variable | Uso | Valor predeterminado |
|---|---|---|
| `BACKEND_PORT` | Puerto publicado del backend en Windows | `8080` |
| `DB_PORT` | Puerto publicado de PostgreSQL en Windows | `5432` |
| `POSTGRES_DB` | Nombre de la base creada por PostgreSQL | `slate_db` |
| `DB_USER` / `DB_PASSWORD` | Credenciales del servicio PostgreSQL | obligatorias por `.env` |
| `DDL_AUTO` | Estrategia local de Hibernate | `update` |
| `JWT_SECRET` | Clave de firma JWT | obligatoria por `.env` |

Dentro de Compose, `DB_URL` se fuerza a `jdbc:postgresql://db:5432/<base>`. No uses `localhost` en esa variable para el backend: dentro de un contenedor, `localhost` apunta al propio contenedor.
