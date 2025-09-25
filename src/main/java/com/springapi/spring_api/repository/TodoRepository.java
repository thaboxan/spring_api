package com.springapi.spring_api.repository;

import com.springapi.spring_api.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    // Find all todos ordered by creation date (newest first)
    List<Todo> findAllByOrderByCreatedAtDesc();
    
    // Find todos by completion status
    List<Todo> findByCompleted(Boolean completed);
    
    // Find todos by title containing text (case insensitive)
    List<Todo> findByTitleContainingIgnoreCase(String title);
    
    // Custom query to find incomplete todos ordered by creation date
    @Query("SELECT t FROM Todo t WHERE t.completed = false ORDER BY t.createdAt DESC")
    List<Todo> findIncompleteTodos();
    
    // Custom query to find completed todos ordered by completion date
    @Query("SELECT t FROM Todo t WHERE t.completed = true ORDER BY t.updatedAt DESC")
    List<Todo> findCompletedTodos();
}
