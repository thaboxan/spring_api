@echo off
echo Starting Todo List Application...
echo.

REM Check if Docker is running
docker version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Docker is not running or not installed.
    echo Please start Docker Desktop and try again.
    pause
    exit /b 1
)

echo Step 1: Starting PostgreSQL database...
docker-compose up -d

if %errorlevel% neq 0 (
    echo ERROR: Failed to start PostgreSQL database.
    echo Please check Docker Desktop and try again.
    pause
    exit /b 1
)

echo Step 2: Waiting for database to be ready...
timeout /t 10 /nobreak > nul

echo Step 3: Starting Spring Boot application...
call mvnw.cmd spring-boot:run

pause
