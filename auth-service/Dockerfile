# Stage 1: Build
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Install curl for the Docker Compose healthcheck
RUN apk add --no-cache curl

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar auth-service.jar

# Expose the port (though we'll use dynamic port 0 in config, 
# it's good practice to document the service intent)
EXPOSE 0

ENTRYPOINT ["java", "-jar", "auth-service.jar"]