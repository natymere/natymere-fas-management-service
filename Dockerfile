# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the jar file into the container
COPY target/fms-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port the application runs on
EXPOSE 8080

# Step 5: Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
