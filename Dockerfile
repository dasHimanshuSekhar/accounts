# Stage 1: Build the app
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the source code
COPY . .

# Build the application (adjust if you use Gradle)
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
