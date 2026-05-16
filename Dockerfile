# ============================================================
# Dockerfile Multi-Etapa para API Spring Boot - Discográfica
# ============================================================
# Este Dockerfile construye y ejecuta la aplicación en dos etapas:
#   1. Compilación: Usa Gradle para compilar el proyecto
#   2. Ejecución: Usa una imagen ligera de Java para ejecutar el JAR
# ============================================================

# ── ETAPA 1: Compilación con Gradle ──────────────────────────
# Usamos la imagen oficial de Gradle con JDK 21
FROM gradle:8-jdk21 AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos TODO el proyecto al contenedor
COPY . .

# Compilamos el proyecto sin ejecutar tests (más rápido en despliegue)
# Esto genera el archivo JAR en /app/build/libs/
RUN gradle clean build -x test --no-daemon

# ── ETAPA 2: Ejecución con JRE ligero ───────────────────────
# Usamos solo el JRE (no necesitamos el JDK completo para ejecutar)
FROM eclipse-temurin:21-jre

# Directorio de trabajo para la aplicación
WORKDIR /app

# Copiamos SOLO el JAR compilado desde la etapa anterior
# El nombre del JAR se basa en: rootProject.name + version del build.gradle
# En este caso: discografia2-1.jar (nombre: discografia2, versión: 1)
COPY --from=build /app/build/libs/discografia2-1.jar app.jar

# Puerto que expone la aplicación (informativo para Render)
EXPOSE 8080

# Comando para ejecutar la aplicación
# Render asignará el puerto mediante la variable de entorno PORT
CMD ["java", "-jar", "app.jar"]
