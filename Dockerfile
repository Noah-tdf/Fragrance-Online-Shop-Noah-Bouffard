# ---- Stage 1: Build the JAR with Gradle + JDK 22 ----
FROM gradle:8.5-jdk22 AS build

# Create and switch to a working directory
WORKDIR /build

# Copy everything (source + Gradle files)
COPY . .

# Build the project, skipping tests
RUN gradle clean build -x test


# ---- Stage 2: Run the built JAR with JDK 22 ----
FROM eclipse-temurin:22-jdk

# Create the runtime directory
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /build/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
