package com.project.foodapp.exceptions;

public class IncorrectCredentialsException extends Exception{
	private static final long serialVersionUID = 1L;

	public IncorrectCredentialsException(String value) {
        super("Incorrect Credentials: " + value);
    }
}