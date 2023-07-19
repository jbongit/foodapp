package com.project.foodapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> CustomerNotFound(CustomerNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

	}
}
