# Stage 1: Build
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Install curl so the healthcheck works
RUN apk add --no-cache curl

COPY --from=build /app/target/*.jar eureka-server.jar

# Standard Eureka Port
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]