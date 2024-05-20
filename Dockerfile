# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build/libs directory to the container
COPY build/libs/spring-boot-login-docker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
