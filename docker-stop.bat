@echo off
echo Stopping Todo List Application...
echo.

REM Stop all containers
docker-compose down

echo.
echo All containers have been stopped.
echo.
echo To remove all data (including database), run:
echo docker-compose down -v
echo.
pause
