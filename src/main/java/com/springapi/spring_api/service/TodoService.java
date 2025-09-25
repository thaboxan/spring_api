package com.springapi.spring_api.service;

import com.springapi.spring_api.entity.Todo;
import com.springapi.spring_api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    // Get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }
    
    // Get todo by ID
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    // Create a new todo
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    // Update an existing todo
    public Todo updateTodo(Long id, Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoDetails.getTitle());
            todo.setDescription(todoDetails.getDescription());
            todo.setCompleted(todoDetails.getCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
    
    // Toggle completion status
    public Todo toggleCompletion(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setCompleted(!todo.getCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
    
    // Delete a todo
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get todos by completion status
    public List<Todo> getTodosByStatus(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    // Search todos by title
    public List<Todo> searchTodos(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Get incomplete todos
    public List<Todo> getIncompleteTodos() {
        return todoRepository.findIncompleteTodos();
    }
    
    // Get completed todos
    public List<Todo> getCompletedTodos() {
        return todoRepository.findCompletedTodos();
    }
}
