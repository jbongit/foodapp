package com.project.foodapp.payloads;

import com.project.foodapp.model.RegistrationDTO;

import lombok.Data;

@Data
public class JwtAuthResponse {
	private String token;
	
	private RegistrationDTO user;
	
	private boolean status;
}
