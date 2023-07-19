package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.Product;
import com.project.foodapp.repository.CustomerRepo;
import com.project.foodapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Customer createCustomer(Customer customer) {
		customerRepo.save(customer);
		return customer;
	}

	@Override
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException{
		Customer customer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		return customer;
	}

	@Override
	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
