
package com.project.foodapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.config.CustomUserDetails;
import com.project.foodapp.exceptions.IncorrectCredentialsException;
import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.payloads.JwtAuthRequest;
import com.project.foodapp.payloads.JwtAuthResponse;
import com.project.foodapp.security.JwtHelper;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class AuthController {
	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/login")
	public void getLoginPage(HttpServletResponse response) throws IOException {
		response.sendRedirect("http://localhost:3000");
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		CustomUserDetails userDetails = (CustomUserDetails)this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		System.out.println(token);
		RegistrationDTO user = RegistrationDTO.builder().userEmailId(userDetails.getUsername())
				.userPassword(userDetails.getPassword()).userId(userDetails.getId()).build();

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(user);
		response.setStatus(true);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new IncorrectCredentialsException("Invalid username or password !!");
		}
	}
}
