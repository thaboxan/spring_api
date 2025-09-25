Write-Host "Starting Todo List Application with Docker..." -ForegroundColor Green
Write-Host ""

# Stop and remove any existing containers
Write-Host "Stopping existing containers..." -ForegroundColor Yellow
docker-compose down

# Build and start all services
Write-Host "Building and starting all services..." -ForegroundColor Yellow
docker-compose up --build -d

# Wait for services to be ready
Write-Host ""
Write-Host "Waiting for services to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Check if services are running
Write-Host ""
Write-Host "Checking service status..." -ForegroundColor Yellow
docker-compose ps

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Todo List Application is ready!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Frontend: http://localhost:3000" -ForegroundColor White
Write-Host "Backend API: http://localhost:8080/api/todos" -ForegroundColor White
Write-Host "Database: localhost:5432 (todolist)" -ForegroundColor White
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Press 'l' to view logs, 's' to stop, or any other key to exit..." -ForegroundColor Yellow

$key = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown").Character

switch ($key) {
    'l' { 
        Write-Host "Showing logs... (Press Ctrl+C to exit logs)" -ForegroundColor Green
        docker-compose logs -f 
    }
    's' { 
        Write-Host "Stopping containers..." -ForegroundColor Yellow
        docker-compose down
        Write-Host "Containers stopped." -ForegroundColor Green
    }
    default { 
        Write-Host "Exiting..." -ForegroundColor Gray
    }
}
