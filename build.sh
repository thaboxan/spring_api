#!/bin/bash
# Build script for Render deployment

echo "Starting build process..."

# Make Maven wrapper executable
chmod +x ./mvnw

# Clean and build the application
./mvnw clean package -DskipTests

echo "Build completed successfully!"
