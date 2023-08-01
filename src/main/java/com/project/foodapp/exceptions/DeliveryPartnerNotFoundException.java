package com.project.foodapp.exceptions;

public class DeliveryPartnerNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public DeliveryPartnerNotFoundException(Long id) {
        super("Delivery Partner not found with ID: " + id);
    }
}