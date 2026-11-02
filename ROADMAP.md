# ROADMAP Y CONTEXTO DEL PROYECTO
## Ingeniería de Software Aplicada – Universidad de Mendoza
### Proyecto: Slate (Backend Spring Boot + Frontend React Native + DevOps en Azure)

---

## 0. Especificación Maestra y Requisitos del Proyecto (Prompt Principal)

> Esta sección contiene la transcripción exacta de la especificación técnica requerida para la cátedra, manteniendo el seguimiento de avance mediante tachado de etapas completadas (`~~etapa~~`).

<details open>
<summary><b>Desplegar Especificación Maestra de Requisitos</b></summary>

### ROL Y OBJETIVO GENERAL
Ingeniero de Software Senior especializado en: Java y Spring Boot, Spring Security y JWT, React Native, Docker y Docker Compose, GitHub Actions, CI/CD, Microsoft Azure (ACR, ACA), PostgreSQL y Azure Database for PostgreSQL, Seguridad (Grype, CVE/CVSS, CodeQL), Monitoreo y observabilidad, Windows 10 (PowerShell, Docker Desktop WSL 2, Git for Windows).

El resultado final debe ser una aplicación funcional, dockerizada, desplegada en Azure, con CI/CD, versionado de imágenes, análisis de vulnerabilidades, autenticación, monitoreo y documentación suficiente para defenderla frente al profesor.

### 1. CONTEXTO ACADÉMICO Y PROYECTOS
- Proyecto de referencia: `goat-grup-one` (Go + Fiber).
- Nuevo proyecto: `slate` (Backend: Spring Boot + PostgreSQL, Frontend: React Native).
- 1.1 Estructura: Repositorios nuevos independientes (`slate-back-isa` y `slate-front-isa`). En Azure se despliega ÚNICAMENTE el backend. Historial limpio de secretos.
- 1.2 Entorno: Windows 10, PowerShell 5.1, Docker Desktop con WSL 2, arquitectura amd64, `.gitattributes` para CRLF/LF.
- 1.3 Base de datos y presupuesto: PostgreSQL. Azure Database for PostgreSQL Flexible Server (`Standard_B1ms`). Crédito de estudiante de USD 100: control de costos estricto, detener servidores al no usarlos, calcular costos antes de crear recursos.
- 1.4 Regla de proyectos originales: Modo solo lectura, nunca modificar originales.

### 2. PROYECTO DE REFERENCIA: goat-grup-one
Auditoría de Dockerfile multietapa distroless, docker-compose.yml, script_az.py (1422 líneas) y workflow GitHub Actions.

---

### ~~3. PRIMERA TAREA: AUDITORÍA COMPLETA~~ - `[x] COMPLETADO`
~~Auditoría exhaustiva de goat-grup-one, slate-backend, slate-mobile y entorno Windows 10. Matriz de auditoría de componentes y plan de trabajo.~~

### 4. FORMA DE TRABAJO: IMPLEMENTACIÓN POR ETAPAS
Estructura obligatoria por etapa con explicación, cambios, comandos PowerShell, verificación y control de costos.

### ~~5. ETAPA 1: PREPARACIÓN DE SPRING BOOT~~ - `[x] COMPLETADO`
~~Compilación, Actuator, perfiles dev/prod, puerto parametrizado, HikariCP y tests aislados con perfil test.~~

### ~~6. ETAPA 2: SEGURIDAD DE SPRING BOOT Y JWT~~ - `[x] COMPLETADO`
~~Auditoría de SecurityConfig, SecurityFilterChain, PasswordEncoder, UserDetailsService, JwtService, JwtAuthenticationFilter, AuthController, rutas públicas y privadas, expiración, firma HMAC-SHA256, endpoint `/api/auth/validate`, colecciones de prueba HTTP.~~

### ~~7. ETAPA 3: BASE DE DATOS (POSTGRESQL) Y HIKARICP~~ - `[x] COMPLETADO`
~~Motor PostgreSQL real, JPA/Hibernate, pool HikariCP optimizado para Azure B1ms, timeouts, separación de credenciales por variables de entorno y soporte SSL (`sslmode=require`).~~

### ~~**8. ETAPA 4: CREACIÓN DEL DOCKERFILE**~~ - `[x] COMPLETADO`
Construcción multietapa Java 21, imagen distroless/slim no-root, etiquetas OCI, arquitectura linux/amd64, .dockerignore.

### ~~9. ETAPA 5: DOCKER COMPOSE~~ - `[x] COMPLETADO`
docker-compose.yml con backend y PostgreSQL 16/17, volúmenes con persistencia, health checks con `pg_isready` y `/actuator/health`.

### ~~10. ETAPA 6: GRYPE, CVE Y CVSS~~ - `[x] COMPLETADO`
Escaneo de vulnerabilidades de la imagen con Grype, informe CVE/CVSS inicial, remediación, escaneo final comparativo.

### ~~11. ETAPA 7: PREPARACIÓN DE MICROSOFT AZURE~~ - `[x] COMPLETADO`
Suscripción estudiante (USD 100), Resource Group, ACR, Container Apps Environment, PostgreSQL Flexible Server, control de costos.

### ~~12. ETAPA 8: SERVICE PRINCIPAL Y SECRETOS~~ - `[x] COMPLETADO`
Creación y configuración de Service Principal con permisos mínimos (RBAC en Resource Group), GitHub Secrets, secretos en Container Apps, .env.example.

### ~~13. ETAPA 9: PUBLICACIÓN MANUAL EN AZURE~~ - `[x] COMPLETADO`
Build manual, push a ACR, deploy a Azure Container Apps, verificación de endpoints y login con JWT en la nube. Adaptación de script_az.py.

### ~~14. ETAPA 10: GITHUB ACTIONS Y CI/CD~~ - `[x] COMPLETADO`
Workflow automatizado: Checkout, Setup Java 21, build/test Maven, Docker build, escaneo Grype, login Azure, push ACR, deploy condicionado.

### ~~15. ETAPA 11: VERSIONADO Y CONDICIONES DE DESPLIEGUE~~ - `[x] COMPLETADO`
Demostración de los 3 casos: (A) CI con imagen en ACR sin deploy, (B) CI + CD con deploy en Azure, (C) Ejecución manual vía workflow_dispatch. Tags con SHA corto.

### ~~16. ETAPA 12: GESTIÓN DE AZURE CONTAINER APPS~~ - `[x] COMPLETADO`
Operaciones CLI y Portal: deploy, start, stop, restart, list, delete, logs y rollback.

### ~~17. ETAPA 13: MONITOREO Y OBSERVABILIDAD~~ - `[x] COMPLETADO`
Actuator, HikariCP metrics, Azure Monitor / Log Analytics, 12 factores (III, V, VII, XI).

### ~~18. ETAPA 14: CODEQL Y SEGURIDAD ADICIONAL~~ - `[x] COMPLETADO`
Análisis estático de código SAST con CodeQL, consultas parametrizadas, validación de inputs.

### 19. ETAPA 15: INTEGRACIÓN CON REACT NATIVE - `[ ] PENDIENTE`
Configuración de URL HTTPS remota de Azure en `slate-front-isa`, prueba de login, almacenamiento de token en SecureStore y consumo de endpoints protegidos.

### 20. ETAPA 16: VALIDACIÓN COMPLETA - `[ ] PENDIENTE`
Auditoría final contra la matriz de 43 requisitos.

### 21. ETAPA 17: PREPARACIÓN PARA EL PARCIAL - `[ ] PENDIENTE`
Guía de defensa, 50 preguntas técnicas con respuesta oral y práctica, simulacro de examen en vivo.

### 22. DOCUMENTACIÓN OBLIGATORIA
README.md y carpeta `docs/` con arquitectura, docker, azure, ci-cd, seguridad, monitoreo y guia-parcial.

</details>

---

## 1. Contexto Académico y Objetivo

El objetivo de este proyecto es trasladar y aplicar todos los conocimientos adquiridos en la cátedra de **Ingeniería de Software Aplicada** sobre un proyecto académico real desarrollado con:
- **Backend:** Java 21 + Spring Boot + Spring Security + JWT + PostgreSQL.
- **Frontend:** React Native con Expo (aplicación móvil).
- **Referencia previa:** Proyecto `goat-grup-one` (desarrollado en Go + Fiber).

El foco no es replicar código de Go, sino diseñar, adaptar e implementar toda la infraestructura moderna de DevOps:
- Dockerización multietapa y optimización de imágenes (distroless/slim, usuario no-root, OCI labels).
- Orquestación local con Docker Compose y persistencia en PostgreSQL.
- Seguridad en contenedores y análisis de vulnerabilidades con Grype (CVE, CVSS) y CodeQL.
- Integración Continua (CI) y Despliegue Continuo (CD) controlado con GitHub Actions.
- Infraestructura en la nube con Microsoft Azure (ACR, Azure Container Apps, PostgreSQL Flexible Server).
- Observabilidad y monitoreo (Spring Boot Actuator, HikariCP, logs y métricas).
- Gestión presupuestaria estricta en Azure (crédito de estudiante de USD 100).

Este documento se mantiene actualizado antes y después de cada fase como constancia del avance técnico y hoja de ruta.

---

## 2. Entorno de Desarrollo (Host Local)

- **Sistema Operativo:** Windows 10 Pro 64-bit (Build 19045 / 22H2).
- **Arquitectura:** `x86_64` (`AMD64`) – coincide con la arquitectura destino de Azure Container Apps (`linux/amd64`).
- **Terminal y Shell:** Windows PowerShell 5.1 (todos los comandos y scripts deben ser nativos para PowerShell, sin sintaxis bash).
- **JDK:** OpenJDK 21.0.10 LTS (BellSoft Liberica) en `C:\Program Files\BellSoft\LibericaJDK-21\`.
- **Maven:** Maven Wrapper 3.9.16 (`mvnw.cmd` / `mvnw`).
- **Docker:** Docker Desktop con backend WSL 2 (distribución `docker-desktop`).
- **Cliente API HTTP:** Bruno (extensión en VS Code) para pruebas de endpoints y colecciones versionadas.
- **Node.js / npm:** Node v25.8.1 / npm 11.11.0.

---

## 3. Estado General de Fases

| Fase | Descripción | Estado | Fecha de Finalización |
| :---: | :--- | :---: | :---: |
| **0** | **Auditoría completa de proyectos, entorno local y presupuesto Azure** | **COMPLETADO** | 31/10/2026 |
| **1** | **Preparación del backend Spring Boot (Actuator, perfiles y build)** | **COMPLETADO** | 31/10/2026 |
| **2** | **Seguridad en Spring Boot, JWT y validación** | **COMPLETADO** | 31/10/2026 |
| **3** | **Base de datos PostgreSQL y pool de conexiones HikariCP** | **COMPLETADO** | 31/10/2026 |
| **4** | **Dockerfile multietapa y construcción de imagen optimizada** | **COMPLETADO** | 31/10/2026 |
| **5** | Docker Compose para entorno local (Backend + PostgreSQL) | **COMPLETADO** | 01/11/2026 |
| **6** | Análisis de vulnerabilidades con Grype (CVE, CVSS) y remediación | **COMPLETADO** | 01/11/2026 |
| **7** | Preparación de infraestructura en Microsoft Azure y control de costos | **COMPLETADO** | 01/11/2026 |
| **8** | Service Principal de Azure y gestión de secretos | **COMPLETADO** | 01/11/2026 |
| **9** | Publicación en ACR y despliegue manual en Azure Container Apps | **COMPLETADO** | 01/11/2026 |
| **10** | Automatización CI/CD con GitHub Actions | **COMPLETADO** | 02/11/2026 |
| **11** | Versionado de imágenes y condiciones de despliegue controlado | **COMPLETADO** | 02/11/2026 |
| **12** | Gestión operativa de Azure Container Apps (start, stop, logs, rollback) | **COMPLETADO** | 02/11/2026 |
| **13** | Monitoreo, métricas, logs y observabilidad (Actuator, Azure Monitor) | **COMPLETADO** | 02/11/2026 |
| **14** | Análisis estático de código con CodeQL y seguridad adicional | **COMPLETADO** | 02/11/2026 |
| **15** | Integración del frontend React Native con el backend en Azure | PENDIENTE | - |
| **16** | Validación integral de requisitos y matriz de pruebas | PENDIENTE | - |
| **17** | Documentación final y preparación para la defensa del examen parcial | PENDIENTE | - |

---

## 4. Registro Detallado de Fases

### FASE 0: Auditoría Completa de Proyectos y Entorno (Completada)

#### Diagnóstico Inicial
Se auditaron los repositorios locales:
- `C:\Users\alexp\Documents\GitHub\slate-back-isa` (nuevo repositorio de backend).
- `C:\Users\alexp\Documents\GitHub\slate-front-isa` (nuevo repositorio de frontend).
- `C:\Users\alexp\Documents\GitHub\goat-grup-one` (proyecto de referencia Go).

#### Matriz de Auditoría de Componentes

| Archivo o componente | Ubicación actual | Función | Estado | Problema detectado | Acción necesaria | Prioridad |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| `pom.xml` | `slate-back-isa/pom.xml` | Gestión de dependencias y build Maven | EXISTE PERO NECESITA MODIFICACIONES | Falta Spring Boot Actuator y dependencias de métricas (HikariCP / Micrometer). | Agregar `spring-boot-starter-actuator` y `micrometer-registry-prometheus`. | Alta |
| `application.yaml` | `slate-back-isa/src/main/resources/application.yaml` | Configuración de Spring Boot | EXISTE PERO NECESITA MODIFICACIONES | No define puerto explícito (`server.port`), sin bloque HikariCP, `ddl-auto: update`, sin perfiles dev/prod ni configuración de Actuator. | Reestructurar con perfiles `dev` y `prod`, configurar HikariCP, Actuator y variables de entorno. | Alta |
| `.env.example` | `slate-back-isa/.env.example` | Plantilla de variables de entorno | EXISTE PERO NECESITA MODIFICACIONES | Inconsistencia: define `JWT_EXPIRATION` mientras `application.yaml` lee `JWT_EXPIRATION_MS`. | Estandarizar variables (`DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION_MS`, `SERVER_PORT`). | Media |
| `.gitattributes` | `slate-back-isa/.gitattributes` | Normalización de saltos de línea | EXISTE PERO NECESITA MODIFICACIONES | Solo normaliza `mvnw` y `*.cmd`. | Agregar reglas LF para `Dockerfile`, scripts `*.sh`, `*.yml`, `*.yaml`. | Media |
| `.gitignore` | `slate-back-isa/.gitignore` | Exclusiones de Git | EXISTE PERO NECESITA MODIFICACIONES | Falta ignorar `.env.local` y reportes temporales. | Agregar exclusiones adicionales de seguridad y reportes locales. | Media |
| `SecurityConfig.java` | `slate-back-isa/src/main/java/com/slate/backend/config/SecurityConfig.java` | Cadena de filtros de Spring Security | EXISTE PERO NECESITA MODIFICACIONES | No tiene configurado CORS ni expone `/actuator/health` en `permitAll()`, bloqueando health checks con 403. | Agregar `permitAll()` para health checks y configurar CORS. | Alta |
| `ApplicationConfig.java` | `slate-back-isa/src/main/java/com/slate/backend/config/ApplicationConfig.java` | Beans de infraestructura de seguridad | EXISTE PERO NECESITA MODIFICACIONES | `DaoAuthenticationProvider` no vincula explícitamente el `PasswordEncoder`. | Inyectar `passwordEncoder()` en el provider. | Media |
| `JwtService.java` | `slate-back-isa/src/main/java/com/slate/backend/security/JwtService.java` | Emisión, firma y validación JWT | EXISTE Y FUNCIONA | Utiliza JJWT 0.12.5 con HS256 y parseo seguro de Claims. | Mantener y cubrir con pruebas unitarias. | Baja |
| `JwtAuthenticationFilter.java` | `slate-back-isa/src/main/java/com/slate/backend/security/JwtAuthenticationFilter.java` | Filtro HTTP OncePerRequest | EXISTE Y FUNCIONA | Extrae el header `Bearer` y valida el token. | Mantener. | Baja |
| `AuthController.java` | `slate-back-isa/src/main/java/com/slate/backend/controller/AuthController.java` | Endpoints de autenticación | EXISTE Y FUNCIONA | Registro (`/api/auth/register`) y login (`/api/auth/login`). | Evaluar endpoint utilitario `/api/auth/validate`. | Media |
| `ClientController.java` | `slate-back-isa/src/main/java/com/slate/backend/controller/ClientController.java` | Endpoints de clientes y deudores | EXISTE Y FUNCIONA | Rutas protegidas multi-tenant por usuario autenticado. | Mantener. | Baja |
| `TransactionController.java` | `slate-back-isa/src/main/java/com/slate/backend/controller/TransactionController.java` | Endpoints de fiados y pagos | EXISTE Y FUNCIONA | Operaciones asociadas a clientes protegidas por JWT. | Mantener. | Baja |
| Tests Unitarios | `slate-back-isa/src/test/...` | Tests de controladores y servicios | EXISTE Y FUNCIONA | 7 clases con Mockito y MockMvc que pasan sin depender de base de datos física. | Mantener. | Media |
| `SlateBackendApplicationTests.java` | `slate-back-isa/src/test/java/com/slate/backend/SlateBackendApplicationTests.java` | Test de integración de carga de contexto | EXISTE PERO NECESITA MODIFICACIONES | Falla al ejecutar `mvn test` si no hay una base de datos PostgreSQL activa. | Configurar perfil de test con base en memoria o propiedades dummy para CI. | Alta |
| `Dockerfile` | `slate-back-isa/Dockerfile` | Imagen Docker del backend | NO EXISTE Y HAY QUE CREARLO | No existe en el proyecto. | Crear Dockerfile multietapa con Java 21, distroless/slim, usuario no-root y OCI labels. | Alta |
| `.dockerignore` | `slate-back-isa/.dockerignore` | Exclusiones para Docker build | NO EXISTE Y HAY QUE CREARLO | No existe en el repositorio. | Crear excluyendo `.git`, `.env`, `target/`, documentación y scripts. | Alta |
| `docker-compose.yml` | `slate-back-isa/docker-compose.yml` | Orquestación local (Backend + PostgreSQL) | NO EXISTE Y HAY QUE CREARLO | No existe en el proyecto. | Crear con servicio `db` (Postgres 16, healthcheck `pg_isready`, volumen) y servicio `backend` (`depends_on: condition: service_healthy`). | Alta |
| Pipeline CI/CD | `slate-back-isa/.github/workflows/` | Automatización GitHub Actions | NO EXISTE Y HAY QUE CREARLO | Solo existe plantilla de PR en `.github/`. | Crear workflow con jobs separados: `ci` (build, tests, docker build, Grype) y `cd` condicional a Azure Container Apps. | Alta |
| Script CLI de Azure | `slate-back-isa/script_az.py` | Administración local de Azure | NO EXISTE Y HAY QUE CREARLO | En `goat-grup-one` es monolítico (1422 líneas) y no pasa variables de base de datos. | Adaptar a un script limpio y modular para Windows 10 / PowerShell con soporte de variables y tags consistentes con CI. | Media |
| Documentación técnica | `slate-back-isa/docs/` | Guías de arquitectura, seguridad y parcial | NO EXISTE Y HAY QUE CREARLO | No existe carpeta `docs/`. | Crear documentación requerida (`arquitectura.md`, `docker.md`, `azure.md`, `ci-cd.md`, `seguridad.md`, `monitoreo.md`, `guia-parcial.md`). | Alta |
| Colección Bruno | `slate-back-isa/docs/bruno/` | Pruebas de API para VS Code | NO EXISTE Y HAY QUE CREARLO | No existe en el repositorio. | Crear colección Bruno con carpetas de Auth, Clientes, Fiados y Actuator para local y Azure. | Media |
| Frontend `api.ts` | `slate-front-isa/src/services/api.ts` | Cliente HTTP en React Native | EXISTE Y FUNCIONA | Consume `EXPO_PUBLIC_API_URL` e inyecta JWT desde `expo-secure-store`. | En Etapa 15 solo se configurará la URL HTTPS de Azure en su `.env`. | Baja |

#### Estimación Presupuestaria de Azure (Crédito de USD 100)
- **Azure Database for PostgreSQL - Flexible Server:** SKU `Standard_B1ms` (~USD 12-15/mes de cómputo + USD 3.68/mes de disco). Estrategia de ahorro: **Detener el servidor cuando no se use** (\$0 cómputo, solo ~USD 0.12/día de disco).
- **Azure Container Registry (ACR):** SKU `Basic` (~USD 5/mes).
- **Azure Container Apps:** Plan Consumo (capa gratuita cubre 180,000 vCPU-s, 360,000 GiB-s y 2M peticiones/mes). Configurado con `minReplicas: 0`.
- **Gasto total estimado durante el desarrollo y defensa:** < USD 8 total.

---

### FASE 1: Preparación de Spring Boot (Completada)

#### Modificaciones Realizadas:
1. **[pom.xml](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/pom.xml):**
   - Incorporación de `spring-boot-starter-actuator` para liveness/readiness probes y observabilidad.
   - Inclusión de `h2` en `<scope>test</scope>` para permitir builds herméticos en CI/CD y entornos locales sin PostgreSQL activo.
2. **[application.yaml](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/resources/application.yaml):**
   - Configuración de `server.port: ${PORT:8080}` configurable por variable de entorno.
   - Definición de pool de conexiones `SlateHikariPool` con tamaños y timeouts explícitos.
   - Configuración de Actuator exponiendo endpoints `/actuator/health`, `/actuator/info`, `/actuator/metrics`.
   - Soporte desacoplado para perfiles `dev` y `prod` (`ddl-auto: validate` en producción).
3. **[SecurityConfig.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/java/com/slate/backend/config/SecurityConfig.java):**
   - Se liberó el acceso sin autenticación a `/actuator/health/**` y `/actuator/info` para que Docker Compose y Azure Container Apps no reciban errores `403 Forbidden`.
   - Se agregó el bean `CorsConfigurationSource` habilitando soporte completo de CORS para clientes web y móviles Expo.
4. **[ApplicationConfig.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/java/com/slate/backend/config/ApplicationConfig.java):**
   - Se inyectó explícitamente el `PasswordEncoder` en `DaoAuthenticationProvider`.
5. **[application-test.yaml](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/test/resources/application-test.yaml) y [SlateBackendApplicationTests.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/test/java/com/slate/backend/SlateBackendApplicationTests.java):**
   - Se aisló la carga de contexto de prueba con `@ActiveProfiles("test")`, permitiendo que la verificación de contexto e inyección de dependencias funcione de forma aislada.
6. **[.env.example](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.env.example) y [.gitattributes](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.gitattributes):**
   - Normalización de variables de entorno (`PORT`, `SPRING_PROFILES_ACTIVE`, `JWT_EXPIRATION_MS`).
   - Normalización de fin de línea `eol=lf` para Dockerfile, scripts `.sh` y `.yaml`.

#### Resultados de Verificación:
- **Ejecución:** `.\mvnw.cmd clean test`
- **Resultado:** `BUILD SUCCESS` (34 tests ejecutados, 0 fallos, 0 errores, 0 omitidos).
- **Actuator:** 3 endpoints expuestos bajo `/actuator`.
- **HikariCP:** Pool `SlateHikariPool` inicializado correctamente.

---

### FASE 2: Seguridad en Spring Boot y JWT (Completada)

#### Modificaciones Realizadas:
1. **[AuthController.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/java/com/slate/backend/controller/AuthController.java):**
   - Incorporación del endpoint `GET /api/auth/validate`: valida el token JWT transmitido en la cabecera `Authorization: Bearer <token>` y retorna los datos del usuario autenticado (`UserProfileResponse`).
2. **[SecurityConfig.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/java/com/slate/backend/config/SecurityConfig.java):**
   - Explicitación de la regla `.requestMatchers("/api/auth/profile", "/api/auth/password", "/api/auth/validate").authenticated()`.
   - Mantenimiento de la arquitectura 100% Stateless (`SessionCreationPolicy.STATELESS`) y desactivación justificada de CSRF para API REST.
3. **[JwtServiceTest.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/test/java/com/slate/backend/security/JwtServiceTest.java):**
   - Creación de suite unitaria para verificar:
     - Generación y extracción de email (`subject`).
     - Validación exitosa con clave y vigencia válidas.
     - Detección y rechazo de tokens con subject discordante.
     - Excepción `ExpiredJwtException` ante tokens vencidos.
     - Excepción `SignatureException` ante tokens firmados con una clave secreta adulterada.
4. **[AuthControllerTest.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/test/java/com/slate/backend/controller/AuthControllerTest.java):**
   - Ampliación de tests MockMvc para:
     - `POST /api/auth/register` (201 Created y retorno de token JWT).
     - `POST /api/auth/register` con email duplicado (409 Conflict).
     - `POST /api/auth/login` con credenciales válidas (200 OK y token).
     - `POST /api/auth/login` con credenciales incorrectas (401 Unauthorized).
     - `GET /api/auth/validate` con token válido (200 OK).
5. **[SecurityIntegrationTest.java](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/test/java/com/slate/backend/security/SecurityIntegrationTest.java):**
   - Suite de integración en vivo contra los filtros reales de Spring Security:
     - Verificación de rutas públicas accesibles sin token (`/actuator/health`, `/api/auth/login`).
     - Verificación de rechazo estricto (`403 Forbidden`) en rutas protegidas (`/api/clients`, `/api/auth/profile`) cuando no se envía token.
     - Verificación de acceso permitido (`200 OK`) a `/api/auth/validate` con token válido.
     - Verificación de rechazo (`403 Forbidden`) ante tokens manipulados o alterados.
6. **Colección Bruno en VS Code ([docs/bruno/](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/bruno)):**
   - Generación de colección nativa en `docs/bruno/` con entornos `Local` y `Azure`.
   - Peticiones pre-configuradas para Auth (registro, login, validación, perfil, cambio de contraseña), Clientes, Transacciones y Actuator.
   - Script post-response automático que guarda el `jwtToken` en la variable de entorno tras el login o registro.

#### Resultados de Verificación:
- **Ejecución:** `.\mvnw.cmd test`
- **Resultado:** `BUILD SUCCESS` (50 tests ejecutados, 0 fallos, 0 errores, 0 omitidos).
- **Cobertura de Seguridad:** Tests unitarios de JwtService, controladores de Auth y pruebas integrales de filtros HTTP pasando en verde.

---

### FASE 3: Base de Datos (PostgreSQL) y HikariCP (Completada)

#### Modificaciones y Acciones Realizadas:
1. **Detección del Motor:** Verificación de PostgreSQL 18.3 x64 activo en el host local (`localhost:5432`).
2. **Configuración de HikariCP en [application.yaml](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/src/main/resources/application.yaml):**
   - Configuración de `SlateHikariPool` con tamaño dinámico (`DB_POOL_MAX: 10` en dev, `5` en prod).
   - Justificación documentada para Azure Database for PostgreSQL Flexible Server (tier `Standard_B1ms` con límite de 50 conexiones del servidor).
   - Definición de timeouts: `idle-timeout: 30000 ms`, `connection-timeout: 20000 ms`, `max-lifetime: 1800000 ms` (30 min para prevenir cortes silenciosos de firewall TCP).
3. **Estrategia DDL:**
   - Perfil `dev`: `ddl-auto: update` (crea y actualiza tablas automáticamente sin intervención manual).
   - Perfil `prod`: `ddl-auto: validate` (garantiza que en Azure no se altere ni destruya el esquema productivo).
4. **Soporte SSL/TLS para Azure:**
   - Documentación y configuración para requerir `sslmode=require` en la cadena JDBC de Azure.
5. **Aislamiento de Secretos:**
   - Verificación de que `.env` está 100% ignorado por Git y Docker.
   - Sincronización de [.env.example](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.env.example).
6. **Documentación Técnica:**
   - Creación de [docs/base-de-datos.md](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/base-de-datos.md) con diagrama ER Mermaid, modelo multi-tenant, parámetros de HikariCP y tabla comparativa Local vs Azure.

#### Resultados de Verificación en Vivo:
- **Arranque:** Spring Boot levantó en el puerto 8080 conectándose a PostgreSQL real en `localhost:5432/slate_db`.
- **Health Check:** `http://localhost:8080/actuator/health` respondió `{"status":"UP"}` con HikariPool conectado.
- **Transacciones e Inserción Real:**
  - Registro de usuario en PostgreSQL -> ID asignado mediante `GenerationType.IDENTITY` (`users`).
  - Login con contraseña cifrada con BCrypt -> Token JWT emitido.
  - Creación de cliente vinculado al usuario -> ID asignado en tabla `clients` con clave foránea `user_id`.
  - Recuperación de clientes -> 1 cliente recuperado en memoria y persistido en PostgreSQL.

---

### FASE 4: Dockerfile Multietapa y Construcción de Imagen (Completada)

#### Archivos Creados:
1. **[.dockerignore](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.dockerignore):**
   - Excluye `.git/`, `.env`, `target/`, `*.md`, `docs/`, `*.log`, scripts temporales.
   - Garantiza que el contexto enviado al daemon de Docker sea mínimo (~131 KB).
2. **[Dockerfile](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/Dockerfile)** — Multietapa:
   - **Etapa `builder`:** `maven:3.9-eclipse-temurin-21-alpine` → `dependency:go-offline` (caché de dependencias desacoplada del código) → `mvn clean package -DskipTests -B`.
   - **Etapa `runtime`:** `eclipse-temurin:21-jre-alpine` (imagen mínima, solo JRE, sin JDK ni herramientas de build).
   - Usuario no-root `spring:spring` (GID/UID 1001) para reducir superficie de ataque.
   - Etiquetas OCI estándar: `org.opencontainers.image.{title, version, created, revision, source, description, vendor, licenses}`.
   - `JAVA_TOOL_OPTIONS` con flags de contenedor: `-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom`.
   - `EXPOSE 8080` y `ENTRYPOINT ["java", "-jar", "/app/app.jar"]`.
3. **Bit de ejecución de `mvnw`:**
   - Ejecutado `git update-index --chmod=+x mvnw` → modo `100755` confirmado.
   - Necesario para que el daemon Docker (Linux) pueda ejecutar el wrapper dentro del builder.

#### Resultados de Verificación:

| Verificación | Resultado |
|:---|:---|
| `docker build` completado | ✅ `BUILD SUCCESS` — 38 fuentes compiladas, JAR de 59 MB empaquetado en ~22s |
| Arquitectura de imagen | ✅ `amd64 linux` (compatible con Azure Container Apps) |
| Tamaño imagen comprimida en registry | ✅ ~130 MB |
| Tamaño imagen sin comprimir (local) | ~404 MB |
| Labels OCI | ✅ 8 campos OCI presentes (`created`, `revision`, `version`, `source`, `title`, `description`, `vendor`, `licenses`) |
| Contexto de build | ✅ Solo 131.58 KB transferidos al daemon (`.dockerignore` eficaz) |
| Spring Boot dentro del contenedor | ✅ Arrancó en 26.67s con perfil `prod` |
| HikariPool desde contenedor | ✅ `SlateHikariPool` conectó a `host.docker.internal:5432` → PostgreSQL 18.3 |
| `/actuator/health` | ✅ `{"status":"UP"}` — `db`, `diskSpace`, `liveness`, `readiness`, `ssl` todos `UP` |
| Shutdown graceful | ✅ `docker stop` completó shutdown graceful de Tomcat y HikariCP |

---

### FASE 5: Docker Compose (Completada)

**Objetivo:** Crear un `docker-compose.yml` que levante el backend y PostgreSQL en contenedores coordinados, con persistencia en volúmenes nombrados, health checks y variables desde `.env`. La validación usará PostgreSQL 16 Alpine y no requiere iniciar el PostgreSQL instalado en Windows.

#### Implementación
1. Se creó [`docker-compose.yml`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docker-compose.yml) con:
   - Servicio `db` basado en `postgres:16-alpine`.
   - Volumen nombrado `slate-postgres-data` y red `slate-network`.
   - Health check de PostgreSQL con `pg_isready`.
   - Servicio `backend` construido desde el Dockerfile, con `depends_on` condicionado a `service_healthy`.
   - `DB_URL` interno apuntando a `jdbc:postgresql://db:5432/...`, nunca a `localhost`.
   - Health check HTTP contra `/actuator/health`.
   - Variables obligatorias interpoladas desde `.env`, sin contraseñas reales en archivos versionados.
2. Se agregó `curl` a la imagen runtime para ejecutar el health check HTTP sin cambiar el usuario no-root.
3. Se actualizaron [`.env.example`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.env.example), [`README.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/README.md) y [`docs/docker.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/docker.md) con el flujo de PowerShell 5.1, persistencia y limpieza.

#### Resultados de Verificación

| Verificación | Resultado |
|:---|:---|
| `docker compose config` | ✅ Configuración válida e interpolación correcta |
| `docker compose up --build -d` | ✅ Imagen construida y servicios iniciados |
| Estado de servicios | ✅ `db` saludable y `backend` saludable |
| `GET /actuator/health` | ✅ `status: UP`, incluyendo conexión PostgreSQL |
| Flujo end-to-end | ✅ Registro, login JWT y `GET /api/clients` funcionando |
| Persistencia | ✅ Login del usuario conservado después de `docker compose down` + `up -d` |
| `.\mvnw.cmd test` | ✅ 50 tests, 0 fallos, 0 errores |
| Secretos en imagen | ✅ La imagen no contiene `DB_PASSWORD` ni `JWT_SECRET` |
| Azure | ✅ No se crearon recursos ni se generaron costos |

La operación detallada, incluyendo la diferencia entre `docker compose down` y `docker compose down -v`, está documentada en [`docs/docker.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/docker.md).

### FASE 6: Grype, CVE y CVSS (Completada)

#### Implementación
1. Se verificó que Grype no estaba instalado localmente y se ejecutó `anchore/grype:latest` dentro de Docker, sin instalar software adicional en Windows.
2. Se escaneó `slate-backend:local` como Docker archive. Grype reportó 44 coincidencias de paquetes y 15 identificadores únicos.
3. Se reemplazó el `curl` agregado al runtime por el `wget` incluido en Alpine BusyBox para conservar el health check sin sumar paquetes runtime. El cambio no altera la aplicación ni el contrato de endpoints.
4. No se sobrescribieron Tomcat/Jackson manualmente: Grype indica versiones corregidas, pero esas dependencias son transitivas del BOM de Spring Boot y requieren una actualización compatible del framework, no un override especulativo.

#### Resultados inicial/final

| Severidad | Inicial | Final | Variación |
|:---|---:|---:|---:|
| Critical | 4 | 4 | 0 |
| High | 10 | 10 | 0 |
| Medium | 19 | 19 | 0 |
| Low | 11 | 11 | 0 |
| **Total de coincidencias** | **44** | **44** | **0** |

Los cuatro hallazgos Critical corresponden a `tomcat-embed-core` 11.0.24, con corrección indicada en 11.0.25. Los High/Medium principales corresponden a Jackson. Los hallazgos de Alpine afectan `coreutils`, BusyBox y GnuPG. El detalle de CVE, CVSS, paquetes, riesgo, remediación, pendientes y comparación con Trivy está en [`docs/seguridad-grype.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/seguridad-grype.md).

#### Verificación posterior

| Verificación | Resultado |
|:---|:---|
| Reconstrucción `docker compose up --build -d` | ✅ Exitosa |
| `db` y `backend` | ✅ Saludables |
| `/actuator/health` | ✅ `UP` |
| Login de prueba local | ✅ Funcionando |
| Tests Maven previos | ✅ 50 tests, 0 fallos |
| Azure/costos | ✅ No se crearon recursos |

### Contexto previo de la FASE 7

Antes de crear cualquier recurso se debía revisar la suscripción de estudiante, crédito disponible, recursos reutilizables, regiones y costo estimado. Esa auditoría está documentada en la sección siguiente.

### FASE 7: Preparación de Microsoft Azure (Completada)

#### Auditoría de solo lectura

- Suscripción activa: **Azure for Students**, habilitada.
- Resource Group existente: `universidad`, región `eastus`.
- ACR reutilizable: `acringenieria`, tier Standard, con repositorio existente de `goat-grup-one`. Ese repositorio y la aplicación original no se modificaron.
- Container Apps: existe `goat-grup-one-env` en Consumption y la app `goat-grup-one`. No se modificaron; Slate requerirá una app y tags propios.
- Log Analytics: se detectaron cuatro workspaces Standard/PerGB2018 con 30 días de retención; no se creó otro.
- PostgreSQL Flexible Server: no existe en el Resource Group. `Microsoft.DBforPostgreSQL` está `NotRegistered` y la consulta de disponibilidad no devolvió `Standard_B1ms` en `eastus`. No se registró el proveedor ni se creó la base.
- RBAC: el usuario tiene permisos Owner a nivel de suscripción y User Access Administrator; hay un Contributor Service Principal en el Resource Group. No se crearon identidades ni roles.
- Budget: no se encontró un presupuesto visible en el Resource Group; debe verificarse en el portal y configurarse solo con autorización.
- Management Groups: la lectura fue rechazada por autorización; no es necesaria para esta fase.

#### Costos y decisión

La recomendación es reutilizar el Resource Group, ACR y, si se autoriza expresamente, el entorno Consumption existente con una Container App separada. PostgreSQL administrado sigue pendiente hasta confirmar SKU/región y costo en Azure Pricing Calculator. El detalle de arquitectura, comandos PowerShell, costos de referencia y controles de crédito está en [`docs/azure.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/azure.md).

No se crearon recursos Azure ni se generaron costos nuevos durante esta fase.

### Contexto previo de la FASE 8

Antes de crear un Service Principal se debía confirmar qué identidad existente podía reutilizarse, definir permisos mínimos sobre el Resource Group, evitar credenciales permanentes innecesarias y preparar GitHub Secrets sin revelar valores. Esa revisión está documentada en la sección de Fase 8.

### FASE 8: Service Principal y secretos (Completada; secrets de GitHub no verificables desde la API disponible)

#### Implementación

- Se creó el Service Principal exclusivo `slate-back-isa-ci`; no se modificó el Service Principal existente.
- Se asignó `Reader` al Resource Group `universidad`.
- Se asignó `AcrPush` únicamente al registry `acringenieria`.
- No se asignó `Owner`, `User Access Administrator`, `Contributor` de suscripción ni `Container Apps Contributor`.
- Se generó una credencial temporal fuera del repositorio para carga manual en GitHub. El secreto no fue incluido en archivos versionados ni en la respuesta.
- Se agregaron [`docs/ci-cd.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/ci-cd.md), la sección de Fase 8 en [`docs/azure.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/azure.md) y placeholders seguros en [`.env.example`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/.env.example).

#### Verificación

| Verificación | Resultado |
|:---|:---|
| Identidad nueva habilitada | ✅ Service Principal creado |
| `Reader` en Resource Group | ✅ Verificado |
| `AcrPush` en ACR | ✅ Verificado |
| Archivo temporal fuera del repositorio | ✅ Credencial fuera del repo; eliminar después de cargarla en GitHub |
| Secretos versionados | ✅ No hay archivos de credenciales rastreados |
| Container Apps original | ✅ No modificada |
| Costos directos de identidad/RBAC | ✅ Sin cargo directo |

### PRÓXIMO PASO: FASE 9 – Publicación manual en ACR y Azure Container Apps

Antes del despliegue se deberá confirmar la credencial en GitHub, crear o autorizar una Container App propia para Slate, calcular el costo de PostgreSQL y obtener autorización explícita para las operaciones que puedan generar consumo.

### FASE 9: Publicación manual en Azure (Completada; el bloqueo inicial quedó resuelto)

El usuario autorizó publicar la imagen y desplegar recursos separados para Slate, con control estricto de costos. La ejecución quedó bloqueada por las políticas de Azure for Students antes de crear recursos: `eastus` informa restricción de aprovisionamiento y `westcentralus` rechaza el recurso por la política de regiones disponibles. No se modificó ningún recurso de `goat-grup-one`.

#### Preparación local verificada

- `.\mvnw.cmd test`: ✅ 50 tests, 0 fallos.
- Imagen `linux/amd64`: ✅ construida.
- Tag inmutable: `slate-backend:e50018636cdc`.
- Usuario no-root: ✅ `spring:spring`.
- Secretos: ✅ no rastreados ni embebidos.
- Push al ACR: no ejecutado porque PostgreSQL no pudo aprovisionarse.
- Imagen publicada posteriormente tras la creación manual de PostgreSQL: ✅ `acringenieria.azurecr.io/slate-backend:e50018636cdc`, digest `sha256:a711502e85aeb6b3c474413bf1941e9fc35c0eb30f4fdddef7618b9e03c3d955`.

#### Operaciones Azure bloqueadas

Se verificó disponibilidad de SKU y precio antes de crear: `eastus` ofrecía `Standard_B1ms` pero rechazó el aprovisionamiento; `westcentralus` ofrecía el SKU pero una policy de la suscripción rechazó el recurso. No se creó PostgreSQL, no se hizo push al ACR, no se creó Container App ni se asignó `Container Apps Contributor`. La estimación y el bloqueo están documentados en [`docs/azure.md`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docs/azure.md).

#### Resultado de la ejecución controlada

- Registro `Microsoft.DBforPostgreSQL`: ✅ realizado; sin cargo directo.
- `eastus` + `Standard_B1ms`: ❌ rechazado por restricción de aprovisionamiento.
- `westcentralus` + `Standard_B1ms`: ❌ rechazado por policy `RequestDisallowedByAzure`.
- PostgreSQL `slate-db-isa`: ✅ confirmado inexistente.
- Imagen `slate-backend:e50018636cdc` en ACR: ✅ confirmado inexistente.
- Container App `slate-back-isa`: ✅ confirmado inexistente.

### Actualización posterior: Container App desplegada

La Container App `slate-back-isa` quedó desplegada en el entorno `slate-isa` con la imagen publicada en ACR. La URL pública responde correctamente:

- `/actuator/health`: `UP`
- Base de datos PostgreSQL: `UP`
- Readiness/liveness: `UP`
- URL: `https://slate-back-isa.calmriver-a6a4e760.canadacentral.azurecontainerapps.io`

La raíz `/` devuelve `403`, comportamiento esperado porque no existe una ruta pública para `/`. El login con el usuario de prueba local devolvió `401`; no se modificaron datos de autenticación automáticamente.

La validación cloud completó el flujo:

- Registro de `danny@gmail.com`: ✅
- Login/JWT: ✅
- `/api/auth/validate`: ✅ perfil `Danny`
- `DDL_AUTO`: cambiado a `validate`
- Container App: `minReplicas=0`
- PostgreSQL `slate-db-isa`: `Stopped`
- Recursos originales `goat-grup-one`: ✅ no modificados.

La Fase 9 queda completada para el alcance aprobado: imagen publicada, Container App y PostgreSQL validados, y ambos cómputos detenidos. El siguiente paso es automatizar CI/CD sin iniciar recursos Azure automáticamente.

### FASE 10: GitHub Actions y CI/CD (En progreso)

- CI automático: tests Maven, build Docker `linux/amd64` y escaneo Grype en pull requests y pushes a `main`.
- CD manual: publicación del tag SHA en `acringenieria.azurecr.io/slate-backend` mediante `workflow_dispatch`.
- No se automatizará el despliegue ni el inicio de `slate-back-isa` o `slate-db-isa`; ambos permanecen detenidos.
- No se modificará `goat-grup-one` ni su repositorio ACR.

#### Implementación y validación

- Workflow creado en [`.github/workflows/ci-cd.yml`](.github/workflows/ci-cd.yml).
- CI: Java 21, Maven tests, Docker `linux/amd64` y Grype con corte `high`.
- CD: `workflow_dispatch` con input explícito `publish`; publica únicamente en `slate-backend` del ACR.
- No ejecuta Container Apps ni PostgreSQL y no requiere `Container Apps Contributor`.
- `.\mvnw.cmd --batch-mode test`: ✅ 50 tests, 0 fallos.
- Build Docker local `linux/amd64`: ✅ validado con tag temporal `slate-backend:ci-phase10`.
- YAML y whitespace: ✅ revisión local; `actionlint` no está instalado.
- Imagen temporal local eliminada después de validar para no conservar espacio innecesario.

La Fase 10 queda completada para el alcance ACR-only. Próximo paso: Fase 11, versionado y condiciones de despliegue.

### FASE 11: Versionado y condiciones de despliegue (En progreso)

- Se cerrará la Fase 10 en un commit antes de modificar el workflow.
- CI no publica automáticamente.
- La publicación requiere `workflow_dispatch`, el input `publish=true` y el environment `azure-acr`.
- Las imágenes se identificarán con el SHA completo del commit; no se usará `latest` como referencia de despliegue.
- No se encenderán PostgreSQL ni Container Apps durante esta fase.

#### Implementación

- Commit de cierre de Fase 10: `d3a49c3`.
- Se confirmó que CI solo ejecuta validación y no publica.
- Se confirmó que la publicación requiere `workflow_dispatch`, `publish=true` y el environment `azure-acr`.
- Se documentaron tags SHA, digest ACR, rollback y aprobación manual en [`docs/ci-cd.md`](docs/ci-cd.md).
- El rollback queda fuera del workflow y requiere autorización explícita para encender recursos.

#### Validación

- YAML revisado localmente; `actionlint` no está instalado.
- Tests Maven: ✅ 50 tests, 0 fallos.
- Build Docker `linux/amd64`: ✅ validado previamente y no se ejecutó publicación.
- No se encendieron PostgreSQL ni Container Apps.

La Fase 11 queda completada. Próximo paso: Fase 12, gestión operativa de Azure Container Apps.

### FASE 12: Gestión operativa de Azure Container Apps (En progreso)

- Recursos actuales: Container App `slate-back-isa` detenida/escalada a cero y PostgreSQL `slate-db-isa` detenido.
- Alcance autorizado: probar temporalmente start, restart, health y logs; apagar todo al finalizar.
- No se ejecutarán actualización de imagen, rollback ni eliminación.
- No se modificará `goat-grup-one`, su entorno ni su repositorio ACR.

#### Implementación y prueba

- Estado/revisions consultados sin modificar `goat-grup-one`.
- Precio revisado antes de iniciar: Container Apps Consumption cobra por uso; PostgreSQL conserva el costo mensual indicado por el Portal.
- PostgreSQL inició temporalmente y llegó a `Ready`.
- Container App quedó `Running`; revision `slate-back-isa--latest` `Healthy`, tráfico 100%.
- `/actuator/health`: ✅ `UP`; base PostgreSQL: ✅ `UP`.
- Reactivación mediante `az containerapp update` con la misma imagen: ✅.
- `az containerapp start` no existe en la CLI instalada.
- `az containerapp revision restart` devolvió error interno del entorno Express.
- `az containerapp logs show` devolvió error `eventStreamEndpoint` del entorno/extensión Express.
- Se documentaron alternativas en [`docs/azure.md`](docs/azure.md): Portal, actualización de revision, Log stream y Log Analytics.
- No se ejecutaron actualización de imagen, rollback ni eliminación.

La Fase 12 queda completada para el alcance autorizado. La Container App queda
encendida temporalmente para que el usuario la apague desde el Portal; PostgreSQL
debe permanecer detenido. Próximo paso: Fase 13, monitoreo y observabilidad.

### FASE 13: Monitoreo y observabilidad (En progreso)

- Baseline: Actuator ya exponía health/info/metrics y Hikari tenía pool `SlateHikariPool`.
- Se mantendrán health/readiness/liveness públicos para probes.
- `metrics` seguirá protegido con JWT.
- No se crearán workspaces, Application Insights ni alertas nuevas.
- Se reutilizarán los workspaces Log Analytics existentes y se controlará la ingestión.
- Al finalizar se dejará `slate-back-isa` con `minReplicas=0` y `slate-db-isa` detenido.

#### Implementación y validación

- Se confirmó que no era necesaria una dependencia Prometheus adicional:
  Actuator/Micrometer registra las métricas Hikari, JDBC, HTTP, JVM y proceso.
- `info.env` quedó deshabilitado para no exponer variables de entorno.
- Se agregó el tag global `application=slate-backend`.
- Se agregaron pruebas de seguridad para `/actuator/metrics`: sin JWT rechaza
  la petición y con JWT válido responde correctamente.
- Se creó [`docs/monitoring.md`](docs/monitoring.md) con Actuator, Hikari,
  Azure Monitor, Log Analytics, costos y factores 12-factor.
- Tests Maven: ✅ 52 tests, 0 fallos.
- Build Docker `linux/amd64`: ✅ validado; imagen temporal eliminada.
- Container App: `minReplicas=0`.
- PostgreSQL: `Stopped`.

La Fase 13 queda completada. Próximo paso: Fase 14, CodeQL y seguridad adicional.

### Actualización de seguridad previa a la Fase 14: Jackson 3

Se corrigió el hallazgo de Jackson 3 reportado por el workflow sin tocar recursos Azure:

- `jackson-bom.version` quedó fijado en `3.1.7`.
- Jackson 3 se resolvió transitivamente desde `spring-boot-starter-webmvc`.
- Jackson 2 se mantuvo en `2.21.7` mediante `jackson-2-bom.version`.
- La imagen `slate-backend:jackson-317` se reconstruyó para `linux/amd64`.
- Grype con `--only-fixed --fail-on high` finalizó con código 0.
- Los tests Maven continuaron pasando (52 tests, 0 fallos).
- No se iniciaron PostgreSQL ni Container Apps y no se publicó una nueva imagen en ACR.

El cambio de `pom.xml` queda listo para commit antes de iniciar la Fase 14.

### FASE 14: CodeQL y seguridad adicional (Completada)

- Se agregó [`.github/workflows/codeql.yml`](.github/workflows/codeql.yml) para
  Java 21 en pull requests, pushes a `main` y ejecución manual.
- El workflow usa `contents: read`, `actions: read` y `security-events: write`;
  no publica imágenes ni despliega Azure.
- La revisión no encontró SQL/JPQL dinámico: los repositorios usan métodos
  derivados de Spring Data.
- Los controladores aplican `@Valid` a los cuerpos y los DTOs principales
  contienen validaciones de Bean Validation.
- Se documentó la integración y el alcance en [`docs/ci-cd.md`](docs/ci-cd.md).
- No se iniciarán PostgreSQL ni Container Apps ni se crearán recursos Azure.

#### Validación

- `.\mvnw.cmd --batch-mode test`: ✅ 52 tests, 0 fallos.
- `git diff --check`: ✅ sin errores.
- El workflow conserva la publicación manual ACR-only y no contiene pasos de
  despliegue o activación de Azure.
- `actionlint` no está instalado en el host; la estructura YAML fue revisada
  manualmente.
- No se modificaron proyectos originales ni se encendieron recursos Azure.

La Fase 14 queda completada. Próximo paso: Fase 15, integración con React Native.
