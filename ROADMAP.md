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

### 9. ETAPA 5: DOCKER COMPOSE - `[ ] PENDIENTE`
docker-compose.yml con backend y PostgreSQL 16/17, volúmenes con persistencia, health checks con `pg_isready` y `/actuator/health`.

### 10. ETAPA 6: GRYPE, CVE Y CVSS - `[ ] PENDIENTE`
Escaneo de vulnerabilidades de la imagen con Grype, informe CVE/CVSS inicial, remediación, escaneo final comparativo.

### 11. ETAPA 7: PREPARACIÓN DE MICROSOFT AZURE - `[ ] PENDIENTE`
Suscripción estudiante (USD 100), Resource Group, ACR, Container Apps Environment, PostgreSQL Flexible Server, control de costos.

### 12. ETAPA 8: SERVICE PRINCIPAL Y SECRETOS - `[ ] PENDIENTE`
Creación y configuración de Service Principal con permisos mínimos (RBAC en Resource Group), GitHub Secrets, secretos en Container Apps, .env.example.

### 13. ETAPA 9: PUBLICACIÓN MANUAL EN AZURE - `[ ] PENDIENTE`
Build manual, push a ACR, deploy a Azure Container Apps, verificación de endpoints y login con JWT en la nube. Adaptación de script_az.py.

### 14. ETAPA 10: GITHUB ACTIONS Y CI/CD - `[ ] PENDIENTE`
Workflow automatizado: Checkout, Setup Java 21, build/test Maven, Docker build, escaneo Grype, login Azure, push ACR, deploy condicionado.

### 15. ETAPA 11: VERSIONADO Y CONDICIONES DE DESPLIEGUE - `[ ] PENDIENTE`
Demostración de los 3 casos: (A) CI con imagen en ACR sin deploy, (B) CI + CD con deploy en Azure, (C) Ejecución manual vía workflow_dispatch. Tags con SHA corto.

### 16. ETAPA 12: GESTIÓN DE AZURE CONTAINER APPS - `[ ] PENDIENTE`
Operaciones CLI y Portal: deploy, start, stop, restart, list, delete, logs y rollback.

### 17. ETAPA 13: MONITOREO Y OBSERVABILIDAD - `[ ] PENDIENTE`
Actuator, HikariCP metrics, Azure Monitor / Log Analytics, 12 factores (III, V, VII, XI).

### 18. ETAPA 14: CODEQL Y SEGURIDAD ADICIONAL - `[ ] PENDIENTE`
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
| **5** | Docker Compose para entorno local (Backend + PostgreSQL) | **PRÓXIMO PASO** | - |
| **6** | Análisis de vulnerabilidades con Grype (CVE, CVSS) y remediación | PENDIENTE | - |
| **7** | Preparación de infraestructura en Microsoft Azure y control de costos | PENDIENTE | - |
| **8** | Service Principal de Azure y gestión de secretos | PENDIENTE | - |
| **9** | Publicación en ACR y despliegue manual en Azure Container Apps | PENDIENTE | - |
| **10** | Automatización CI/CD con GitHub Actions | PENDIENTE | - |
| **11** | Versionado de imágenes y condiciones de despliegue controlado | PENDIENTE | - |
| **12** | Gestión operativa de Azure Container Apps (start, stop, logs, rollback) | PENDIENTE | - |
| **13** | Monitoreo, métricas, logs y observabilidad (Actuator, Azure Monitor) | PENDIENTE | - |
| **14** | Análisis estático de código con CodeQL y seguridad adicional | PENDIENTE | - |
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

### PRÓXIMO PASO: FASE 5 – Docker Compose

**Objetivo:** Crear un `docker-compose.yml` que levante el backend y PostgreSQL en contenedores coordinados, con persistencia en volúmenes nombrados, health checks y variables desde `.env`.

**Tareas a ejecutar en la Fase 5:**
1. Crear [`docker-compose.yml`](file:///C:/Users/alexp/Documents/GitHub/slate-back-isa/docker-compose.yml) con:
   - Servicio `db`: `postgres:16-alpine`, volumen nombrado `postgres_data`, healthcheck con `pg_isready`.
   - Servicio `backend`: `build: .`, `depends_on: db: condition: service_healthy`, env vars desde `.env` (con `DB_URL` apuntando al servicio `db`), puerto `8080:8080`, healthcheck contra `/actuator/health`.
2. Ajustar `.env.example` para cubrir todas las variables necesarias para Compose.
3. Verificar el stack completo con `docker compose up --build`.
4. Probar endpoints end-to-end (register → login → clients) contra el stack de contenedores.
5. Documentar el stack en `docs/docker.md`.


