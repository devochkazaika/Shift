FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/content-maker.jar

# Copy the application.yml file into the container
COPY src/main/resources/application.yml /app/application.yml

# Expose the port the application will run on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "content-maker.jar", "--spring.config.location=/app/application.yml"]