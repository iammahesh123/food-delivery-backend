# Use an official Eclipse Temurin JDK image for the builder stage
FROM eclipse-temurin:17-jdk-jammy AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven Wrapper files
COPY mvnw .
COPY .mvn/ .mvn

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application using Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Use a smaller Eclipse Temurin JRE image for the final stage
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar ./app.jar

# Expose the port your application will run on
EXPOSE 8080

# Run as a non-root user for security
RUN adduser --system --no-create-home appuser
USER appuser

# Health check to ensure the application is running
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]