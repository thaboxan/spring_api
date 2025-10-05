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

## Setup Instructions

### Manual Setup Process

1. **Ensure Docker Desktop is running** on your machine
2. **Start the PostgreSQL database container** using Docker Compose
3. **Run the Spring Boot application** using the Maven wrapper
4. **Access the application**:
   - Web Frontend: `http://localhost:8080`
   - API: `http://localhost:8080/api/todos`

## Application Management

### Starting the Application
1. Launch Docker Desktop and wait for it to be fully running
2. Start the PostgreSQL container from the docker-compose configuration
3. Start the Spring Boot application using Maven

### Stopping the Application
1. Stop the Spring Boot application (interrupt the process)
2. Stop the PostgreSQL container using Docker Compose

## Using the Application

### Web Interface
1. Open `http://localhost:8080` in your browser
2. Add new todos using the form
3. Mark todos as complete/incomplete by clicking the toggle button
4. Delete todos using the delete button
5. Filter todos using the buttons (All, Incomplete, Completed)

### API Testing

You can test the API endpoints using:
- Web browser for GET requests
- Postman or similar API testing tools
- Browser developer tools
- Any HTTP client application

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

### Request Examples
- **Create a Todo**: Send POST request to `/api/todos` with JSON body containing title and description
- **Get All Todos**: Send GET request to `/api/todos`
- **Toggle Completion**: Send PATCH request to `/api/todos/{id}/toggle`
- **Delete a Todo**: Send DELETE request to `/api/todos/{id}`

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

### Testing
- Run unit tests using Maven wrapper test command
- Tests are located in `src/test/java`

### Building
- Build the application using Maven wrapper clean package command
- Generated JAR file will be in `target/` directory

### Database Management
- Stop the PostgreSQL container using Docker Compose down command
- To reset all data, stop containers and remove volumes

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

1. **Database Connection Issues**: Ensure PostgreSQL container is running via Docker Compose
2. **Port Already in Use**: Check if port 8080 or 5432 is already in use by another application
3. **Docker Issues**: Ensure Docker Desktop is running and you have sufficient permissions
4. **Application Startup**: Verify Java 21 is properly installed and configured
5. **Browser Access**: Clear browser cache if web interface doesn't load properly

## Next Steps

- Add user authentication
- Implement todo categories/tags
- Add due dates for todos
- Create a more sophisticated frontend (React, Angular, etc.)
- Add pagination for large todo lists
- Implement todo sharing between users
