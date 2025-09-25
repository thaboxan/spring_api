# Todo List Application - Docker Setup

This is a complete Todo List application with Docker containers for the frontend, backend, and database.

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │    Backend      │    │   Database      │
│   (Nginx)       │    │  (Spring Boot)  │    │  (PostgreSQL)   │
│   Port: 3000    │────│   Port: 8080    │────│   Port: 5432    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Services

### 1. Frontend Container
- **Image**: Nginx Alpine
- **Port**: 3000
- **Purpose**: Serves the HTML/CSS/JavaScript frontend
- **Location**: `./frontend/`

### 2. Backend Container
- **Image**: Custom (OpenJDK 21)
- **Port**: 8080
- **Purpose**: Spring Boot REST API
- **Database**: Connects to PostgreSQL container

### 3. Database Container
- **Image**: PostgreSQL 15
- **Port**: 5432
- **Database**: `todolist`
- **User**: `postgres`
- **Password**: `admin`

## Quick Start

### Prerequisites
- Docker Desktop installed and running
- Docker Compose available

### Option 1: Using Batch Script (Windows)
```bash
docker-start.bat
```

### Option 2: Using PowerShell Script (Windows)
```powershell
.\docker-start.ps1
```

### Option 3: Manual Docker Compose
```bash
# Build and start all services
docker-compose up --build -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## Access Points

Once all containers are running:

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/todos
- **Database**: localhost:5432 (use any PostgreSQL client)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| POST | `/api/todos` | Create a new todo |
| GET | `/api/todos/{id}` | Get todo by ID |
| PUT | `/api/todos/{id}` | Update todo |
| PATCH | `/api/todos/{id}/toggle` | Toggle completion |
| DELETE | `/api/todos/{id}` | Delete todo |
| GET | `/api/todos/completed` | Get completed todos |
| GET | `/api/todos/incomplete` | Get incomplete todos |

## Example API Usage

### Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Docker", "description": "Complete Docker tutorial"}'
```

### Get All Todos
```bash
curl http://localhost:8080/api/todos
```

## Docker Commands

### Start Services
```bash
docker-compose up -d
```

### Stop Services
```bash
docker-compose down
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres-db
```

### Rebuild Services
```bash
docker-compose up --build -d
```

### Remove Everything (including data)
```bash
docker-compose down -v
```

## Development

### Building Individual Containers

#### Backend
```bash
docker build -t todo-backend .
```

#### Frontend
```bash
docker build -t todo-frontend ./frontend/
```

### Accessing Database
```bash
# Connect to PostgreSQL container
docker exec -it todo-postgres-db psql -U postgres -d todolist

# View tables
\dt

# Query todos
SELECT * FROM todos;
```

## File Structure

```
spring_api/
├── docker-compose.yml          # Main orchestration file
├── Dockerfile                  # Backend container
├── docker-start.bat           # Windows start script
├── docker-start.ps1           # PowerShell start script
├── docker-stop.bat            # Windows stop script
├── .dockerignore              # Docker ignore file
│
├── frontend/                   # Frontend container
│   ├── Dockerfile
│   ├── index.html
│   └── nginx.conf
│
├── src/                        # Spring Boot source
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-docker.properties
│   └── test/
│
└── target/                     # Build output
```

## Environment Variables

The application uses different configuration profiles:

- **Local Development**: `application.properties`
- **Docker Environment**: `application-docker.properties`

### Key Differences

| Setting | Local | Docker |
|---------|-------|---------|
| Database Host | `localhost` | `postgres-db` |
| Database Port | `5432` | `5432` |
| API URL (Frontend) | `localhost:8080` | `window.location.hostname:8080` |

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Check what's using the port
   netstat -an | findstr :3000
   netstat -an | findstr :8080
   netstat -an | findstr :5432
   ```

2. **Database connection failed**
   ```bash
   # Check if PostgreSQL container is healthy
   docker-compose ps
   
   # View database logs
   docker-compose logs postgres-db
   ```

3. **Frontend can't connect to backend**
   - Ensure both containers are on the same network
   - Check backend container logs
   - Verify CORS configuration

4. **Services won't start**
   ```bash
   # Check Docker daemon
   docker info
   
   # Remove all containers and rebuild
   docker-compose down -v
   docker-compose up --build -d
   ```

### Health Checks

The Docker Compose file includes health checks:

- **Database**: Checks PostgreSQL readiness
- **Backend**: Checks API endpoint availability

### Logs Location

Container logs can be viewed with:
```bash
docker-compose logs [service-name]
```

## Production Considerations

For production deployment, consider:

1. **Environment Variables**: Use external configuration
2. **Secrets Management**: Don't hardcode passwords
3. **SSL/TLS**: Enable HTTPS
4. **Reverse Proxy**: Use nginx or traefik
5. **Monitoring**: Add health monitoring
6. **Backup**: Implement database backup strategy

## Support

If you encounter issues:

1. Check the logs: `docker-compose logs -f`
2. Ensure Docker Desktop is running
3. Verify port availability
4. Try rebuilding: `docker-compose up --build -d`
