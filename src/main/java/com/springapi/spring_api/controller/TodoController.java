package com.springapi.spring_api.controller;

import com.springapi.spring_api.dto.TodoRequest;
import com.springapi.spring_api.entity.Todo;
import com.springapi.spring_api.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
@Tag(name = "Todos", description = "CRUD operations for Todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Get all todos")
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @Operation(summary = "Get a todo by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new todo")
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted());
        Todo created = todoService.createTodo(todo);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Update an existing todo")
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        Todo todoDetails = new Todo();
        todoDetails.setTitle(request.getTitle());
        todoDetails.setDescription(request.getDescription());
        todoDetails.setCompleted(request.getCompleted());
        Todo updated = todoService.updateTodo(id, todoDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Toggle completion status of a todo")
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleCompletion(@PathVariable Long id) {
        Todo updated = todoService.toggleCompletion(id);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a todo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get todos by completion status")
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByStatus(@PathVariable Boolean completed) {
        return ResponseEntity.ok(todoService.getTodosByStatus(completed));
    }

    @Operation(summary = "Search todos by title")
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String title) {
        return ResponseEntity.ok(todoService.searchTodos(title));
    }

    @Operation(summary = "Get incomplete todos")
    @GetMapping("/incomplete")
    public ResponseEntity<List<Todo>> getIncompleteTodos() {
        return ResponseEntity.ok(todoService.getIncompleteTodos());
    }

    @Operation(summary = "Get completed todos")
    @GetMapping("/completed")
    public ResponseEntity<List<Todo>> getCompletedTodos() {
        return ResponseEntity.ok(todoService.getCompletedTodos());
    }
}
