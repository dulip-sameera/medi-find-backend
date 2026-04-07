# Stage 1: Build stage
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
# We switch to eclipse-temurin, which is stable and maintained
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Install curl so the healthcheck works
RUN apk add --no-cache curl

# Copy only the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar config-server.jar

# Expose the port
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "config-server.jar"]