# ==============================================================================
# Etapa 1: Build y compilación (Maven 3.9 + Eclipse Temurin 21 JDK en Alpine)
# ==============================================================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# 1. Copiar descriptor de proyecto primero para aprovechar el sistema de caché de capas de Docker
COPY pom.xml ./

# Descargar dependencias para dejarlas cacheadas si el pom.xml no cambia
RUN mvn dependency:go-offline -B

# 2. Copiar el código fuente y compilar el JAR ejecutable
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==============================================================================
# Etapa 2: Runtime ligero, minimalista y seguro (JRE 21 Alpine)
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine AS runtime

# Argumentos para trazabilidad y metadatos OCI
ARG BUILD_DATE
ARG VCS_REF
ARG VERSION=0.0.1-SNAPSHOT

# Metadatos estándar OCI (Open Container Initiative)
LABEL org.opencontainers.image.title="slate-backend" \
      org.opencontainers.image.description="Backend Spring Boot para Slate - ISA UM" \
      org.opencontainers.image.version="${VERSION}" \
      org.opencontainers.image.created="${BUILD_DATE}" \
      org.opencontainers.image.revision="${VCS_REF}" \
      org.opencontainers.image.source="https://github.com/DannyOkk/slate-back-isa" \
      org.opencontainers.image.vendor="Universidad de Mendoza" \
      org.opencontainers.image.licenses="Apache-2.0"

WORKDIR /app

# Crear usuario y grupo sin privilegios para mitigar riesgos de seguridad (principio de menor privilegio)
RUN addgroup -S spring && adduser -S spring -G spring

RUN apk del --no-cache gnupg gnupg-dirmngr gnupg-gpgconf gnupg-keyboxd \
    gnupg-utils gnupg-wks-client gpg gpg-agent \
    gpg-wks-server gpgsm gpgv 2>/dev/null || true

# Copiar el JAR empaquetado desde la etapa de construcción asignando propiedad al usuario spring
COPY --from=builder --chown=spring:spring /build/target/slate-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Variables de entorno y optimizaciones de JVM para contenedores
ENV PORT=8080 \
    SPRING_PROFILES_ACTIVE=prod \
    JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"

# Cambiar al usuario sin privilegios
USER spring:spring

# Documentar puerto de escucha
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
