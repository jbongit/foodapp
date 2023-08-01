package com.project.foodapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;
import com.project.foodapp.model.DeliveryPartner;
import com.project.foodapp.model.DeliveryPartnerDTO;
import com.project.foodapp.model.Restaurant;
import com.project.foodapp.model.RestaurantDTO;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.DeliveryPartnerService;
import com.project.foodapp.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	DeliveryPartnerService deliveryPartnerService;
	
	
	@PostMapping("/customer")
	public ResponseEntity<Customer> createCustomerProfile(@RequestBody @Valid CustomerDTO customer,BindingResult bindingResult) throws IllegalArgumentException {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer,bindingResult));
	}
	
	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> createrestaurantProfile(@RequestBody @Valid RestaurantDTO customer,BindingResult bindingResult) throws IllegalArgumentException {
		return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(customer,bindingResult));
	}
	
	@PostMapping("/deliverypartner")
	public ResponseEntity<DeliveryPartner> createDeliveryPartnerProfile(@RequestBody @Valid DeliveryPartnerDTO deliveryPartnerDTO,BindingResult bindingResult) throws IllegalArgumentException {
		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPartnerService.createDeliveryPartner(deliveryPartnerDTO,bindingResult));
	}
}
