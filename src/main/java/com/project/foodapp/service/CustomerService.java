package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.Product;

public interface CustomerService {
	public Customer createCustomer(Customer customer);
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException;
	public Customer updateCustomer(Long id,Customer updatedCustomer);
	public Customer deleteCustomer(Long id);
	
	public List<Product> getAllProducts();
}
