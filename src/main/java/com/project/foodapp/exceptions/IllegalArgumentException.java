package com.project.foodapp.exceptions;

public class IllegalArgumentException extends Exception{
	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String message) {
		super(message);
	}
}
