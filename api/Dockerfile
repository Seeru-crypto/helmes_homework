# Stage 1: Build the project
FROM gradle:jdk17 AS builder

WORKDIR /app

# Copying necessary files for building the project
COPY build.gradle .
COPY src ./src

# Running gradle build to compile the project
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM openjre:17-jre-alpine

WORKDIR /app

# Copying the built JAR file from the previous stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Exposing the port the application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
