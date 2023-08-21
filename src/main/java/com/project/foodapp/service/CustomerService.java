package com.project.foodapp.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;
import com.project.foodapp.model.RegistrationDTO;

import jakarta.validation.Valid;

public interface CustomerService {
	
	public Customer createCustomer(@Valid CustomerDTO customer, BindingResult bindingResult) throws IllegalArgumentException;
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException;
	public Customer updateCustomer(Long id,CustomerDTO updatedCustomer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException;
	public Customer deleteCustomer(Long id) throws CustomerNotFoundException;
	public List<Customer> getAllCustomers();
	public RegistrationDTO findByEmailId(String emailId);
}
