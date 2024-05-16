# Use an OpenJDK 17 runtime as a base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight base image for the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the previous stage into the container at the specified path
COPY --from=build /app/target/*.jar /app/my-app.jar

# Expose the port that the application runs on
EXPOSE 8000

# Command to run the application when the container starts
CMD ["java", "-jar", "my-app.jar"]
