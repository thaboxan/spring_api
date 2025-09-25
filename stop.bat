@echo off
echo Stopping Todo List Application...
echo.

echo Stopping Spring Boot application...
REM The application will be stopped by Ctrl+C when running

echo Stopping PostgreSQL database...
docker-compose down

echo.
echo Todo List application stopped successfully.
pause
