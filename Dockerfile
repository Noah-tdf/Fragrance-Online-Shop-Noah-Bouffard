# ---- Stage 1: Build the JAR using Gradle + JDK 17 ----
FROM gradle:8.5-jdk17 AS build
WORKDIR /build
COPY . .
RUN gradle clean build -x test

# ---- Stage 2: Run the app using lightweight JDK 17 ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
