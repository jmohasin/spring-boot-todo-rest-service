package com.todo.springboot.rest.example.todo;

public class TodoNotFoundException extends RuntimeException {

	public TodoNotFoundException(String exception) {
		super(exception);
	}

}
