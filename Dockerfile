# --- Stage 1: Build the Spring Boot application ---
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml) first to leverage Docker cache
COPY pom.xml .

# Download dependencies - only if pom.xml changes
RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Build the Spring Boot application into an executable JAR
RUN mvn clean package -DskipTests

# --- Stage 2: Create the final runtime image ---
# Use a smaller Eclipse Temurin JRE (Java Runtime Environment) image for the final application.
# The 'alpine' variant is known for being extremely small.
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Expose the port Spring Boot will run on
EXPOSE 8081

# Copy the executable JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Command to run the Spring Boot application
# We tell Spring Boot to use this PORT.
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]