# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy only the compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Standard Spring Boot port
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]