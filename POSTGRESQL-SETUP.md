# PostgreSQL Setup Guide

## Quick Start Steps:

### 1. Ensure Docker Desktop is Running
- Launch Docker Desktop application
- Wait for the Docker Desktop icon in system tray to show "Running" status
- This initialization process usually takes 30-60 seconds

### 2. Start PostgreSQL Database
Once Docker Desktop is fully running:
- Navigate to the project directory in your terminal
- Use Docker Compose to start the PostgreSQL container
- The container will be configured with the settings defined in docker-compose.yml

### 3. Verify Database is Running
- Check that the PostgreSQL container is running using Docker container listing
- Verify the container is listening on port 5432
- Confirm the database is accessible

### 4. Start Spring Boot Application
- Use Maven wrapper to start the Spring Boot application
- The application will automatically connect to the PostgreSQL container
- Access the application through your web browser

## Database Configuration Details:
- **Database**: PostgreSQL 15
- **Host**: localhost
- **Port**: 5432
- **Database Name**: todolist
- **Username**: postgres
- **Password**: admin

## Troubleshooting:

### If PostgreSQL container fails to start:
1. Verify Docker Desktop is running and responsive
2. Check Docker container logs for error messages
3. Restart the container using Docker Compose

### If Spring Boot can't connect to database:
1. Confirm PostgreSQL container is running and accessible
2. Test database connection using Docker exec to connect to container
3. Verify application.properties contains correct database configuration
4. Check that port 5432 is not blocked by firewall or used by other applications

### Data Persistence:
- Data is stored in Docker volume named `postgres_data`
- All todo data persists across container restarts
- To completely reset all data, stop containers and remove associated volumes
- Database schema is automatically created by Hibernate on first startup

## Testing CRUD Operations:

Once everything is running, test your application:
1. Open web browser and navigate to: http://localhost:8080
2. Test creating new todos using the web form
3. Test reading todos by viewing the list on the main page
4. Test updating todos by marking them as complete/incomplete
5. Test deleting todos using the delete buttons
6. Restart the application and verify that all data persists

## Configuration Files:
- `application.properties` - Contains PostgreSQL connection settings
- `docker-compose.yml` - Defines PostgreSQL container configuration
- `application-docker.properties` - Docker-specific database configuration
- `Dockerfile` - Application containerization instructions