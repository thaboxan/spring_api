#!/bin/bash
# Start script for Render deployment

echo "Starting Spring Boot application..."
echo "Using profile: ${SPRING_PROFILES_ACTIVE}"
echo "Database URL configured: ${DATABASE_URL:0:50}..." 

# Run the application with production profile
java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -Dserver.port=${PORT:-8080} target/spring_api-0.0.1-SNAPSHOT.war
