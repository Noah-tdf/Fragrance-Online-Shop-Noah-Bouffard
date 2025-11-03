# ---- Stage 1: Build the JAR with Gradle ----
FROM gradle:8.5-jdk21 AS build

# Install JDK 22 manually
RUN apt-get update && apt-get install -y wget && \
    wget https://download.oracle.com/java/22/latest/jdk-22_linux-x64_bin.deb && \
    apt install -y ./jdk-22_linux-x64_bin.deb && \
    rm jdk-22_linux-x64_bin.deb && \
    update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-22/bin/java 1 && \
    update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-22/bin/javac 1 && \
    java -version

WORKDIR /build
COPY . .
RUN gradle clean build -x test


# ---- Stage 2: Run the built JAR on JDK 22 ----
FROM eclipse-temurin:22-jdk

WORKDIR /app
COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
