# PostgreSQL Setup Guide

## Quick Start Steps:

### 1. Ensure Docker Desktop is Running
- Docker Desktop should be starting automatically now
- Wait for the Docker Desktop icon in system tray to show "Running"
- This usually takes 30-60 seconds

### 2. Start PostgreSQL Database
Once Docker Desktop is running, use one of these methods:

**Method A: Use the startup script**
```cmd
start-with-postgresql.bat
```

**Method B: Manual startup**
```cmd
docker-compose up -d postgres-db
```

### 3. Verify Database is Running
```cmd
check-database.bat
```

### 4. Start Spring Boot Application
```cmd
mvn spring-boot:run
```

## Database Configuration Details:
- **Database**: PostgreSQL 15
- **Host**: localhost
- **Port**: 5432
- **Database Name**: todolist
- **Username**: postgres
- **Password**: admin

## Troubleshooting:

### If PostgreSQL container fails to start:
1. Check Docker Desktop is running: `docker ps`
2. Check container logs: `docker-compose logs postgres-db`
3. Restart container: `docker-compose down && docker-compose up -d postgres-db`

### If Spring Boot can't connect to database:
1. Verify PostgreSQL is running: `docker ps`
2. Test connection: `docker exec todo-postgres-db psql -U postgres -d todolist -c "SELECT 1;"`
3. Check application.properties configuration

### Data Persistence:
- Data is stored in Docker volume `postgres_data`
- Data persists across container restarts
- To reset data: `docker-compose down -v` (removes volumes)

## Testing CRUD Operations:

Once everything is running, test your application:
1. Navigate to: http://localhost:8080
2. Test creating, reading, updating, and deleting todos
3. Restart the application and verify data persists

## Files Created/Modified:
- `application.properties` - PostgreSQL connection settings
- `docker-compose.yml` - PostgreSQL container configuration  
- `start-with-postgresql.bat` - Automated startup script
- `check-database.bat` - Database status checker