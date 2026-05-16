# ── ETAPA 1: Compilación con Gradle ──────────────────────────
# Usamos la imagen oficial de Gradle con JDK 21
FROM gradle:8-jdk21 AS build

WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/discografia2-1.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
