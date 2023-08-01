package com.project.foodapp.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;

import jakarta.validation.Valid;

public interface CustomerService {
	public Customer createCustomer(@Valid CustomerDTO customer, BindingResult bindingResult) throws IllegalArgumentException;
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException;
	public Customer updateCustomer(Long id,CustomerDTO updatedCustomer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException;
	public Customer deleteCustomer(Long id) throws CustomerNotFoundException;
	public List<Customer> getAllCustomers();
	
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException;
	public CartItem removeFromCartByProductId(Long custId, Long productId) throws CartItemNotFoundException;
	public CartItem updateCartItemQuantity(Long quantity, Long custId, Long productId) throws CartItemNotFoundException;
	
	
}
