package com.springapi.spring_api.controller;

import com.springapi.spring_api.entity.Todo;
import com.springapi.spring_api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // Enable CORS for frontend integration
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    // Get all todos
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
    
    // Get todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = todoService.getTodoById(id);
        if (todo.isPresent()) {
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Create a new todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        try {
            Todo createdTodo = todoService.createTodo(todo);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    // Update an existing todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Toggle completion status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleCompletion(@PathVariable Long id) {
        Todo updatedTodo = todoService.toggleCompletion(id);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Delete a todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Get todos by completion status
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByStatus(@PathVariable Boolean completed) {
        List<Todo> todos = todoService.getTodosByStatus(completed);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
    
    // Search todos by title
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String title) {
        List<Todo> todos = todoService.searchTodos(title);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
    
    // Get incomplete todos
    @GetMapping("/incomplete")
    public ResponseEntity<List<Todo>> getIncompleteTodos() {
        List<Todo> todos = todoService.getIncompleteTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
    
    // Get completed todos
    @GetMapping("/completed")
    public ResponseEntity<List<Todo>> getCompletedTodos() {
        List<Todo> todos = todoService.getCompletedTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}
