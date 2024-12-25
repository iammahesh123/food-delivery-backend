FROM maven:3.8.3-openjdk-11-slim AS builder

# Add Maintainer Info
LABEL maintainer="maheshkadambala18@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=./target/food-backend-svc.jar

# Add the application's jar to the container
ADD ${JAR_FILE} food-backend-svc.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/food-backend-svc.jar"]