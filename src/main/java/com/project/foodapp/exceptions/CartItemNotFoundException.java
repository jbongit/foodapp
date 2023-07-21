package com.project.foodapp.exceptions;

public class CartItemNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public CartItemNotFoundException(Long id) {
        super("CartItem not found with ID: " + id);
    }
}