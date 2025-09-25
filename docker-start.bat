@echo off
echo Starting Todo List Application with Docker...
echo.

REM Stop and remove any existing containers
echo Stopping existing containers...
docker-compose down

REM Build and start all services
echo Building and starting all services...
docker-compose up --build -d

REM Wait for services to be ready
echo.
echo Waiting for services to start...
timeout /t 10 /nobreak >nul

REM Check if services are running
echo.
echo Checking service status...
docker-compose ps

echo.
echo ============================================
echo Todo List Application is starting up!
echo ============================================
echo Frontend: http://localhost:3000
echo Backend API: http://localhost:8080/api/todos
echo Database: localhost:5432 (todolist)
echo ============================================
echo.
echo Press any key to view logs or Ctrl+C to exit...
pause >nul

REM Show logs
docker-compose logs -f
