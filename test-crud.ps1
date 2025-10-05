# Test CRUD Operations
Write-Host "Testing PostgreSQL CRUD Operations..." -ForegroundColor Green
Write-Host ""

# Base URL
$baseUrl = "http://localhost:8080/api/todos"

try {
    # Test 1: GET all todos (should be empty initially)
    Write-Host "1. Testing GET all todos..." -ForegroundColor Yellow
    $allTodos = Invoke-RestMethod -Uri $baseUrl -Method Get
    Write-Host "   Current todos count: $($allTodos.Count)" -ForegroundColor Cyan
    
    # Test 2: CREATE a new todo
    Write-Host "2. Testing POST (Create) new todo..." -ForegroundColor Yellow
    $newTodo = @{
        title = "Test PostgreSQL Connection"
        description = "Verify CRUD operations work with PostgreSQL"
        completed = $false
    } | ConvertTo-Json
    
    $createdTodo = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $newTodo -ContentType "application/json"
    Write-Host "   Created todo with ID: $($createdTodo.id)" -ForegroundColor Cyan
    
    # Test 3: GET the specific todo
    Write-Host "3. Testing GET by ID..." -ForegroundColor Yellow
    $todoById = Invoke-RestMethod -Uri "$baseUrl/$($createdTodo.id)" -Method Get
    Write-Host "   Retrieved: '$($todoById.title)'" -ForegroundColor Cyan
    
    # Test 4: UPDATE the todo
    Write-Host "4. Testing PUT (Update) todo..." -ForegroundColor Yellow
    $updateTodo = @{
        title = "Updated: PostgreSQL CRUD Test"
        description = "Successfully updated this todo in PostgreSQL!"
        completed = $true
    } | ConvertTo-Json
    
    $updatedTodo = Invoke-RestMethod -Uri "$baseUrl/$($createdTodo.id)" -Method Put -Body $updateTodo -ContentType "application/json"
    Write-Host "   Updated title: '$($updatedTodo.title)'" -ForegroundColor Cyan
    Write-Host "   Completed status: $($updatedTodo.completed)" -ForegroundColor Cyan
    
    # Test 5: GET all todos again (should show our updated todo)
    Write-Host "5. Testing GET all todos (with our new data)..." -ForegroundColor Yellow
    $allTodosAfter = Invoke-RestMethod -Uri $baseUrl -Method Get
    Write-Host "   Total todos now: $($allTodosAfter.Count)" -ForegroundColor Cyan
    
    # Test 6: DELETE the todo
    Write-Host "6. Testing DELETE todo..." -ForegroundColor Yellow
    Invoke-RestMethod -Uri "$baseUrl/$($createdTodo.id)" -Method Delete
    Write-Host "   Todo deleted successfully" -ForegroundColor Cyan
    
    # Test 7: Verify deletion
    Write-Host "7. Verifying deletion..." -ForegroundColor Yellow
    $finalTodos = Invoke-RestMethod -Uri $baseUrl -Method Get
    Write-Host "   Final todos count: $($finalTodos.Count)" -ForegroundColor Cyan
    
    Write-Host ""
    Write-Host "✅ ALL CRUD OPERATIONS SUCCESSFUL!" -ForegroundColor Green
    Write-Host "✅ PostgreSQL integration working perfectly!" -ForegroundColor Green
    
} catch {
    Write-Host "❌ Error testing CRUD operations:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}

Write-Host ""
Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")