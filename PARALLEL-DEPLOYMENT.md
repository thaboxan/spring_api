# Parallel Deployment Guide: Container + VM

This guide explains how to run both Container and VM deployments simultaneously without port conflicts.

## Port Configuration

| Environment | Service | Port | Access URL |
|-------------|---------|------|------------|
| **Container** | PostgreSQL | 5433 | localhost:5433 |
| **Container** | Spring Boot | 8081 | http://localhost:8081 |
| **Container** | Frontend | 3001 | http://localhost:3001 |
| **VM** | Spring Boot | 8082 | http://localhost:8082 |

## Deployment Steps

### 1. Start Container Environment
```bash
# Start containers (includes shared PostgreSQL database)
docker-compose up -d

# Check containers are running
docker ps
```

### 2. Start VM Environment
```bash
# In your Ubuntu WSL / VM, navigate to project directory
cd ~/spring_api

# Run Spring Boot application (connects to container's database)
./mvnw spring-boot:run

# Or specify profile explicitly
./mvnw spring-boot:run -Dspring-boot.run.profiles=default
```

### 3. Alternative: Use VM Profile
```bash
# Run with VM-specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=vm
```

## Database Connection Strategy

Both environments connect to the **same PostgreSQL database** running in the container:
- **Container Spring Boot**: Connects via Docker network (`postgres-db:5432`)
- **VM Spring Boot**: Connects via host port mapping (`localhost:5433`)

## Testing Both Environments

### Container API:
```bash
curl http://localhost:8081/api/todos
```

### VM API:
```bash
curl http://localhost:8082/api/todos
```

### Frontend (only available via container):
```bash
curl http://localhost:3001
```

## Troubleshooting

### If VM fails to start:
1. **Check if container database is running:**
   ```bash
   docker ps | grep postgres
   ```

2. **Check if ports are available:**
   ```bash
   sudo lsof -i :8082
   sudo lsof -i :5433
   ```

3. **Kill conflicting processes:**
   ```bash
   sudo kill -9 <PID>
   ```

### If container fails to start:
1. **Stop any running Spring Boot processes:**
   ```bash
   pkill -f spring-boot
   ```

2. **Check Docker ports:**
   ```bash
   docker-compose ps
   ```

## Environment Variables

The `.env` file controls container behavior:
```env
POSTGRES_PORT=5433
SERVER_PORT=8081
FRONTEND_PORT=3001
```

VM behavior is controlled by `application.properties` and `application-vm.properties`.

## Benefits of This Setup

- ✅ **Shared Database**: Both environments use same data
- ✅ **No Port Conflicts**: Each service has unique ports
- ✅ **Independent Testing**: Test container vs VM behavior
- ✅ **Easy Switching**: Can run either or both simultaneously