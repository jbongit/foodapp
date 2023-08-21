package com.project.foodapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> CustomerNotFoundHandler(CustomerNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<String> CartItemNotFoundHandler(CartItemNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> ProductNotFoundHandler(ProductNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<String> RestaurantNotFoundHandler(RestaurantNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	

	@ExceptionHandler(DeliveryPartnerNotFoundException.class)
	public ResponseEntity<String> DeliveryPartnerNotFoundHandler(DeliveryPartnerNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(ProductRestaurantNotFoundException.class)
	public ResponseEntity<String> ProductRestaurantNotFoundExceptionHandler(ProductRestaurantNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(IncorrectCredentialsException.class)
	public ResponseEntity<String> IncorrectCredentialsExceptionHandler(IncorrectCredentialsException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> AccessDeniedExceptionHandler(AccessDeniedException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}
}
