package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;

public interface CustomerService {
	public Customer createCustomer(Customer customer);
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException;
	public Customer updateCustomer(Long id,Customer updatedCustomer) throws CustomerNotFoundException;
	public Customer deleteCustomer(Long id) throws CustomerNotFoundException;
	public List<Customer> getAllCustomers();
	
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException;
	public CartItem removeFromCartByProductId(Long custId, Long productId) throws CartItemNotFoundException;
	public CartItem updateCartItemQuantity(Long quantity, Long custId, Long productId) throws CartItemNotFoundException;
	
}
