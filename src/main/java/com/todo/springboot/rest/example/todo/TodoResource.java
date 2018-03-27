package com.todo.springboot.rest.example.todo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*")
@RestController
public class TodoResource {

	@Autowired
	private TodoRepository todoRepository;

	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos() {
		return todoRepository.findAll();
	}

	@GetMapping("/todos/{id}")
	public Todo retrieveTodo(@PathVariable long id) {
		Optional<Todo> todo = todoRepository.findById(id);

		if (!todo.isPresent())
			throw new TodoNotFoundException("id-" + id);

		return todo.get();
	}

	@DeleteMapping("/todos/{id}")
	public void deleteTodo(@PathVariable long id) {
		todoRepository.deleteById(id);
	}

	@PostMapping("/todos")
	public ResponseEntity<Object> createTodo(@RequestBody Todo todo) {
		Todo savedtodo = todoRepository.save(todo);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedtodo.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<Object> updateTodo(@RequestBody Todo todo, @PathVariable long id) {

		Optional<Todo> todoOptional = todoRepository.findById(id);

		if (!todoOptional.isPresent())
			return ResponseEntity.notFound().build();

		todo.setId(id);
		
		todoRepository.save(todo);

		return ResponseEntity.noContent().build();
	}
}
