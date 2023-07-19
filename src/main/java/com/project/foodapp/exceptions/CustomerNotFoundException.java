package com.project.foodapp.exceptions;

public class CustomerNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Long id) {
        super("Customer not found with ID: " + id);
    }
}