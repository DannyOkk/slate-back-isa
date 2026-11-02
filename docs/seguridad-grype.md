# Fase 6: Grype, CVE y CVSS

## Alcance y método

Se analizó la imagen local `slate-backend:local` con Grype ejecutado dentro de Docker. No se instaló Grype en Windows ni se crearon recursos Azure. La imagen se exportó como archivo Docker archive para que el contenedor de Grype pudiera inspeccionarla.

- Grype: `0.121.0`
- Plataforma del escáner: `linux/amd64`
- Imagen analizada: `slate-backend:local`
- Imagen base runtime: `eclipse-temurin:21-jre-alpine`
- Fecha de los escaneos: 01/11/2026

El primer resultado de tabla y el escaneo JSON final reportaron el mismo conjunto de vulnerabilidades: **44 coincidencias de paquetes**, correspondientes a **15 identificadores únicos**.

## Resultado inicial y final

| Severidad | Inicial | Final | Variación |
|---|---:|---:|---:|
| Critical | 4 | 4 | 0 |
| High | 10 | 10 | 0 |
| Medium | 19 | 19 | 0 |
| Low | 11 | 11 | 0 |
| **Total de coincidencias** | **44** | **44** | **0** |

El conteo es por paquete afectado. Un mismo CVE/advisory puede aparecer varias veces porque alcanza componentes distintos del mismo paquete lógico o módulos de una distribución.

## Identificadores encontrados

| ID | Severidad | CVSS observado | Paquetes afectados | Corrección indicada por Grype |
|---|---|---:|---|---|
| GHSA-9xv2-5v5q-p794 | Critical | 9.8 | `tomcat-embed-core` 11.0.24 | 11.0.25 |
| GHSA-f525-44xv-f2qj | Critical | 9.8 | `tomcat-embed-core` 11.0.24 | 11.0.25 |
| GHSA-gcx9-497g-6cp6 | Critical | 9.1 | `tomcat-embed-core` 11.0.24 | 11.0.25 |
| GHSA-h3x4-894j-xpx5 | Critical | 9.1 | `tomcat-embed-core` 11.0.24 | 11.0.25 |
| GHSA-7hhh-6rmp-j9qf | High | 7.5 | `jackson-core` 2.21.5 / 3.1.5 | 2.21.7 / 3.1.7 |
| GHSA-cxp5-3px4-pw24 | High | 7.5 | `jackson-databind` 2.21.5 / 3.1.5 | 2.21.7 |
| GHSA-p6pp-m3f8-5c89 | High | 7.5 | `jackson-core` 2.21.5 / 3.1.5 | 2.21.7 / 3.1.7 |
| GHSA-q4xh-88c3-wmh7 | High | 7.5 | `jackson-databind` 2.21.5 / 3.1.5 | 2.21.6 |
| GHSA-wv8q-qhhj-9h54 | High | 7.5 | `jackson-databind` 2.21.5 / 3.1.5 | 2.21.7 |
| GHSA-gx83-3vf8-gh7j | Medium | 5.6 | `jackson-databind` | 2.21.6 |
| GHSA-wjgm-6hv5-3cvf | Medium | 5.3 | `jackson-databind` | 2.21.6 |
| CVE-2016-2781 | Medium | 6.5 | `coreutils` modules | No fixed version reported |
| CVE-2025-60876 | Medium | 6.5 | `busybox`, `ssl_client` | No fixed version reported |
| CVE-2026-56391 / CVE-2026-56392 | Medium | 6.1 | `coreutils` modules | No fixed version reported |
| CVE-2022-3219 | Low | 3.3 | GnuPG modules | No fixed version reported |

CVSS es el valor disponible en la base de datos de Grype; no se inventaron puntuaciones para los identificadores sin dato adicional. La severidad y el vector deben confirmarse contra la fuente del proveedor antes de una decisión de producción.

## Remediación aplicada

Se eliminó la instalación explícita de `curl` en la etapa runtime. El health check de Compose usa el `wget` ya incluido en Alpine BusyBox:

```yaml
test: ["CMD", "wget", "--spider", "--quiet", "http://localhost:8080/actuator/health"]
```

Esto reduce paquetes agregados a la imagen y mantiene el usuario no-root. La reconstrucción fue exitosa; Compose quedó saludable, `/actuator/health` respondió `UP` y el login JWT continuó funcionando. Esta reducción no cambió el conjunto de vulnerabilidades reportado por Grype: las coincidencias restantes provienen de la imagen base y de dependencias transitivas gestionadas por Spring Boot.

No se forzaron versiones de Tomcat o Jackson manualmente. Aunque Grype indica versiones corregidas, sobrescribir componentes administrados por el BOM de Spring Boot sin una actualización compatible del framework puede romper el runtime. Queda como acción futura actualizar Spring Boot/BOM en una tarea separada, ejecutar toda la suite y volver a escanear.

## Riesgo y pendientes

- **Tomcat Critical:** componente HTTP directamente relacionado con el servidor web; requiere prioridad máxima. La aplicación no debe publicarse en Azure con estos hallazgos sin evaluar una versión compatible del BOM.
- **Jackson High/Medium:** riesgo asociado al procesamiento JSON; el impacto depende de los vectores concretos y del uso de deserialización.
- **Alpine Medium/Low:** paquetes del runtime; minimizar la imagen reduce superficie, pero la corrección depende de una nueva imagen base publicada.
- El escaneo no demostró que la imagen esté libre de vulnerabilidades; demostró el estado concreto de la imagen en esta fecha y con esta base de datos.

## Grype frente a Trivy

| Aspecto | Grype | Trivy |
|---|---|---|
| Enfoque | Escáner de vulnerabilidades centrado en SBOM y ecosistema Anchore/Syft | Escáner amplio de vulnerabilidades, secretos, misconfiguraciones y licencias |
| Integración natural | Syft, generación y consumo de SBOM | CLI única para imágenes, filesystem, repositorios y configuración |
| Salidas | Tabla, JSON, SARIF y otros formatos | Tabla, JSON, SARIF y otros formatos |
| Uso recomendado aquí | Inventario/CVE reproducible de la imagen backend | Complemento posterior para secretos y misconfiguraciones |

Grype no reemplaza tests ni CodeQL. Un pipeline completo puede usar ambos, pero sus conteos pueden diferir por base de datos, alias, alcance y momento del escaneo.
