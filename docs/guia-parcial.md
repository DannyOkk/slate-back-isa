# Guía de defensa de Slate

## Preguntas técnicas y respuestas breves

1. **¿Qué problema resuelve Slate?** Digitaliza fiados, pagos, clientes,
   deudores y recordatorios para comercios pequeños.
2. **¿Qué componentes tiene?** Backend Spring Boot, PostgreSQL, frontend Expo
   React Native, Docker, GitHub Actions y Azure.
3. **¿Por qué separar backend y frontend?** Permite desplegar y versionar cada
   parte de forma independiente.
4. **¿Qué versión de Java usa el backend?** Java 21.
5. **¿Qué versión de Expo usa el frontend?** Expo SDK 58.
6. **¿Qué es JWT?** Un token firmado que representa la autenticación del
   usuario sin mantener una sesión servidor tradicional.
7. **¿Dónde se guarda el JWT en Android?** En `expo-secure-store`.
8. **¿Qué ocurre en web?** Se usa el fallback web existente y nunca se guardan
   secretos de backend en variables públicas.
9. **¿Qué rutas de autenticación son públicas?** Registro y login.
10. **¿Qué protege las demás rutas?** Spring Security y el filtro JWT.
11. **¿Cómo se firma el token?** Con HMAC-SHA256 y un secreto configurado fuera
   de la imagen.
12. **¿Cómo se validan entradas?** DTOs con Bean Validation y `@Valid`.
13. **¿Cómo se manejan errores?** Mediante excepciones de dominio y un handler
   global con respuestas HTTP explícitas.
14. **¿Por qué no se usa SQL concatenado?** Los repositorios usan Spring Data y
   métodos derivados, evitando inyección SQL.
15. **¿Qué base de datos se usa?** PostgreSQL.
16. **¿Qué es HikariCP?** El pool de conexiones JDBC usado para controlar
   cantidad, tiempos y reutilización de conexiones.
17. **¿Qué cambia entre dev, test y prod?** Configuración de base, logging,
   validación de esquema y secretos.
18. **¿Por qué `DDL_AUTO=validate` en Azure?** Para detectar incompatibilidades
   sin modificar automáticamente el esquema productivo.
19. **¿Qué hace Docker Compose?** Coordina backend y PostgreSQL localmente con
   red, volumen y health checks.
20. **¿Por qué usar un volumen nombrado?** Para conservar datos al recrear
   contenedores.
21. **¿Qué ventaja tiene el Dockerfile multietapa?** Separa compilación y
   runtime, reduciendo la imagen final.
22. **¿Por qué ejecutar como usuario no-root?** Reduce el impacto de una
   eventual vulnerabilidad dentro del contenedor.
23. **¿Qué arquitectura se publica?** `linux/amd64`, compatible con Azure
   Container Apps usado en el proyecto.
24. **¿Qué hace `.dockerignore`?** Evita enviar secretos, documentación,
   artefactos y archivos innecesarios al contexto de build.
25. **¿Qué analiza Grype?** Vulnerabilidades conocidas de paquetes e imagen.
26. **¿Qué diferencia hay entre CVE y CVSS?** CVE identifica el fallo y CVSS
   estima su severidad.
27. **¿Qué corrige CodeQL?** Detecta patrones de seguridad y errores de código
   mediante análisis estático.
28. **¿Cuándo corre CI?** En pull requests y pushes a `main`.
29. **¿Cuándo se publica a ACR?** En pushes a `main` y en ejecuciones manuales;
   los pull requests solo validan.
30. **¿Por qué no usar `latest`?** Porque es mutable; los tags SHA identifican
   exactamente el commit.
31. **¿Qué es rollback?** Volver manualmente a un tag SHA validado anterior.
32. **¿Qué permisos tiene el Service Principal?** Reader sobre el grupo,
   AcrPush sobre el registro y permiso mínimo para actualizar la Container App
   Slate; no permisos amplios de suscripción.
33. **¿Cuándo se despliega Azure?** Solo con `[deploy]` en un push a `main` o
   con `workflow_dispatch` y `deploy=true`; un push normal solo publica.
34. **¿Qué servicios Azure usa Slate?** ACR, Container Apps, PostgreSQL
   Flexible Server y el entorno de observabilidad existente.
35. **¿Cómo se controlan costos?** Se calcula precio antes de crear recursos y
   se detienen PostgreSQL y las réplicas cuando no se usan.
36. **¿Qué no se modificó nunca?** `goat-grup-one` ni sus recursos.
37. **¿Qué endpoint permite verificar salud?** `/actuator/health`.
38. **¿Qué métricas se observan?** HTTP, JVM, proceso, JDBC y HikariCP.
39. **¿Por qué proteger `/actuator/metrics`?** Porque puede revelar información
   operativa sensible.
40. **¿Qué hace CORS?** Controla qué orígenes pueden consumir la API; la
   configuración actual permite las pruebas del frontend.
41. **¿Cómo consume API el frontend?** Mediante un cliente central configurado
   con `EXPO_PUBLIC_API_URL`.
42. **¿Cómo maneja `401` el frontend?** Limpia el token y devuelve al usuario al
   flujo de autenticación.
43. **¿Qué estados debe mostrar una pantalla de datos?** Carga, error, vacío y
   contenido.
44. **¿Qué calcula el módulo de deudores?** Lista y totaliza saldos pendientes.
45. **¿Cómo se calcula el riesgo?** El backend evalúa saldo, actividad y reglas
   de negocio, y devuelve `BAJO`, `MEDIO` o `ALTO`.
46. **¿Cómo se registran movimientos?** Con tipo `FIADO` o `PAGO`, monto y
   descripción opcional.
47. **¿Qué pasa si se intenta borrar un cliente con movimientos?** El backend
   lo rechaza para preservar integridad histórica.
48. **¿Qué limitación tiene Expo Web?** Notificaciones locales y controles
   nativos deben validarse en Android.
49. **¿Qué prepara `eas.json`?** Perfiles development, preview y production,
   sin generar builds automáticamente.
50. **¿Qué falta para un build EAS real?** Autenticar la cuenta, ejecutar
   `eas init`, revisar el `projectId` y luego autorizar el build.

## Demostración sugerida

1. Mostrar el login y el registro.
2. Crear un cliente y registrar un fiado.
3. Mostrar saldo, riesgo y pantalla de deudores.
4. Registrar un pago y comprobar saldo actualizado.
5. Mostrar health, métricas protegidas, Docker Compose y workflow CI/CD.
6. Explicar tags SHA, Grype, CodeQL, secretos y control de costos.
