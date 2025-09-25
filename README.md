# Todo List Application

A complete Todo List application built with Spring Boot, PostgreSQL, and Docker.

## Features

- ✅ Create, read, update, and delete todos
- ✅ Mark todos as complete/incomplete
- ✅ Filter todos by status (all, completed, incomplete)
- ✅ Search todos by title
- ✅ Persistent storage with PostgreSQL
- ✅ RESTful API
- ✅ Simple web frontend
- ✅ Docker containerization

## Prerequisites

- Java 21
- Maven (included via wrapper)
- Docker Desktop (must be running)

## Quick Start

### Option 1: Use the Startup Script (Recommended)

1. **Start Docker Desktop** on your machine
2. **Run the startup script**:
   ```cmd
   start.bat
   ```
3. **Open your browser** and go to `http://localhost:8080`

### Option 2: Manual Setup

1. **Start Docker Desktop**

2. **Start the PostgreSQL database**:
   ```cmd
   docker-compose up -d
   ```

3. **Run the Spring Boot application**:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

4. **Access the application**:
   - Web Frontend: `http://localhost:8080`
   - API: `http://localhost:8080/api/todos`

## Stopping the Application

### Option 1: Use the Stop Script
```cmd
stop.bat
```

### Option 2: Manual Stop
1. Press `Ctrl+C` in the terminal running the Spring Boot application
2. Stop the database:
   ```cmd
   docker-compose down
   ```

## Using the Application

### Web Interface
1. Open `http://localhost:8080` in your browser
2. Add new todos using the form
3. Mark todos as complete/incomplete by clicking the toggle button
4. Delete todos using the delete button
5. Filter todos using the buttons (All, Incomplete, Completed)

### API Usage Examples

#### Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos ^
  -H "Content-Type: application/json" ^
  -d "{\"title\": \"Learn Spring Boot\", \"description\": \"Complete the Spring Boot tutorial\"}"
```

#### Get All Todos
```bash
curl http://localhost:8080/api/todos
```

#### Toggle Todo Completion
```bash
curl -X PATCH http://localhost:8080/api/todos/1/toggle
```

#### Delete a Todo
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| GET | `/api/todos/{id}` | Get todo by ID |
| POST | `/api/todos` | Create a new todo |
| PUT | `/api/todos/{id}` | Update a todo |
| PATCH | `/api/todos/{id}/toggle` | Toggle completion status |
| DELETE | `/api/todos/{id}` | Delete a todo |
| GET | `/api/todos/incomplete` | Get incomplete todos |
| GET | `/api/todos/completed` | Get completed todos |
| GET | `/api/todos/search?title={title}` | Search todos by title |

## Example API Usage

### Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Spring Boot", "description": "Complete the Spring Boot tutorial"}'
```

### Get All Todos
```bash
curl http://localhost:8080/api/todos
```

### Toggle Todo Completion
```bash
curl -X PATCH http://localhost:8080/api/todos/1/toggle
```

### Delete a Todo
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## Database Schema

The `todos` table has the following structure:

```sql
CREATE TABLE todos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Development

### Running Tests

```bash
mvnw test
```

### Building the Application

```bash
mvnw clean package
```

### Stopping the Database

```bash
docker-compose down
```

To remove the database volume as well:

```bash
docker-compose down -v
```

## Technology Stack

- **Backend**: Spring Boot 3.5.6
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Frontend**: HTML, CSS, JavaScript

## Project Structure

```
src/
├── main/
│   ├── java/com/springapi/spring_api/
│   │   ├── controller/
│   │   │   └── TodoController.java
│   │   ├── entity/
│   │   │   └── Todo.java
│   │   ├── repository/
│   │   │   └── TodoRepository.java
│   │   ├── service/
│   │   │   └── TodoService.java
│   │   ├── ServletInitializer.java
│   │   └── SpringApiApplication.java
│   └── resources/
│       ├── static/
│       │   └── index.html
│       └── application.properties
└── test/
    └── java/com/springapi/spring_api/
        └── SpringApiApplicationTests.java
```

## Troubleshooting

1. **Database Connection Issues**: Make sure PostgreSQL is running via Docker Compose
2. **Port Already in Use**: Check if port 8080 or 5432 is already in use
3. **Docker Issues**: Ensure Docker is running and you have sufficient permissions

## Next Steps

- Add user authentication
- Implement todo categories/tags
- Add due dates for todos
- Create a more sophisticated frontend (React, Angular, etc.)
- Add pagination for large todo lists
- Implement todo sharing between users
