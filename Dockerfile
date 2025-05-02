# Use official Eclipse Temurin JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY lib/aarithmetic.jar app.jar

# Run the application
ENTRYPOINT [ "java", "-jar", "app.jar" ]
