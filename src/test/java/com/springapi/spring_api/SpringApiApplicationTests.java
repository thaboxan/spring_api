package com.springapi.spring_api;

import com.springapi.spring_api.entity.Todo;
import com.springapi.spring_api.repository.TodoRepository;
import com.springapi.spring_api.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
	"spring.datasource.url=jdbc:h2:mem:testdb",
	"spring.datasource.driver-class-name=org.h2.Driver",
	"spring.jpa.hibernate.ddl-auto=create-drop"
})
class SpringApiApplicationTests {

	@Autowired
	private TodoService todoService;

	@Autowired
	private TodoRepository todoRepository;

	@Test
	void contextLoads() {
		// Test that the application context loads successfully
		assertNotNull(todoService);
		assertNotNull(todoRepository);
	}

	@Test
	void testTodoCreation() {
		// Test creating a new todo
		Todo todo = new Todo("Test Todo", "This is a test todo");
		Todo savedTodo = todoService.createTodo(todo);
		
		assertNotNull(savedTodo);
		assertNotNull(savedTodo.getId());
		assertEquals("Test Todo", savedTodo.getTitle());
		assertEquals("This is a test todo", savedTodo.getDescription());
		assertFalse(savedTodo.getCompleted());
		assertNotNull(savedTodo.getCreatedAt());
	}

	@Test
	void testTodoToggleCompletion() {
		// Test toggling todo completion status
		Todo todo = new Todo("Toggle Test", "Test toggling completion");
		Todo savedTodo = todoService.createTodo(todo);
		
		// Initially should be incomplete
		assertFalse(savedTodo.getCompleted());
		
		// Toggle to complete
		Todo toggledTodo = todoService.toggleCompletion(savedTodo.getId());
		assertNotNull(toggledTodo);
		assertTrue(toggledTodo.getCompleted());
		
		// Toggle back to incomplete
		Todo toggledBackTodo = todoService.toggleCompletion(savedTodo.getId());
		assertNotNull(toggledBackTodo);
		assertFalse(toggledBackTodo.getCompleted());
	}
}
