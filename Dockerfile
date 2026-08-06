# Construcción
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /workspace

COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml

RUN chmod +x mvnw

COPY src src

RUN ./mvnw clean package -DskipTests \
    && JAR_FILE="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' | head -n 1)" \
    && test -n "$JAR_FILE" \
    && cp "$JAR_FILE" /tmp/app.jar

# Ejecución
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S app \
    && adduser -S app -G app

COPY --from=build --chown=app:app /tmp/app.jar /app/app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]