#!/bin/bash
# Start script for Render deployment

echo "Starting Spring Boot application..."

# Run the application with production profile
java -jar -Dspring.profiles.active=prod target/spring_api-0.0.1-SNAPSHOT.war
