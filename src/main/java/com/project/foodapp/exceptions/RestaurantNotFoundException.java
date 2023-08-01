package com.project.foodapp.exceptions;

public class RestaurantNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(Long id) {
        super("Restaurant not found with ID: " + id);
    }
}