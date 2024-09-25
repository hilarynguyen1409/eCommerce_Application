# Use the OpenJDK 11 image to run the app
FROM openjdk:11-jre-slim

# Copy the built JAR file from the target directory
COPY target/auth-course-0.0.1-SNAPSHOT.jar /app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
