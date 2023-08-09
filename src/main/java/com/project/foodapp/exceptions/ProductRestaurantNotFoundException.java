package com.project.foodapp.exceptions;

public class ProductRestaurantNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public ProductRestaurantNotFoundException(Long productId,Long restaurantId) {
        super("Product ID: "+ productId+ " not found with Restaurant ID: " + restaurantId);
    }
}