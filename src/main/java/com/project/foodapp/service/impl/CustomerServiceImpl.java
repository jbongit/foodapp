package com.project.foodapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.Product;
import com.project.foodapp.repository.CartRepo;
import com.project.foodapp.repository.CustomerRepo;
import com.project.foodapp.repository.ProductRepo;
import com.project.foodapp.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartRepo cartRepo;

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException{
		Customer customer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		return customer;
	}

	@Override
	public Customer updateCustomer(Long id, Customer updatedCustomer) throws CustomerNotFoundException {
		Customer existingCustomer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		existingCustomer.setCustName(updatedCustomer.getCustName());
		existingCustomer.setCustEmailId(updatedCustomer.getCustEmailId());
		existingCustomer.setCustAddress(updatedCustomer.getCustAddress());
		existingCustomer.setCustMobileno(updatedCustomer.getCustMobileno());
		return customerRepo.save(existingCustomer);
	}

	@Override
	public Customer deleteCustomer(Long id) throws CustomerNotFoundException {
		Customer existingCustomer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		customerRepo.deleteById(id);
		return existingCustomer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException {
		Product product=productRepo.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
		
		if(cartRepo.findByProductIdandCustId(custId, productId).isPresent()) {
			return updateCartItemQuantity(quantity, custId, productId);
		}
		
		CartItem cartItem=CartItem.builder().custId(custId).product(product).quantity(quantity).build();
		return cartRepo.save(cartItem);
	}

	@Override
	@Transactional
	public CartItem removeFromCartByProductId(Long custId,Long productId) throws CartItemNotFoundException{
		CartItem existingCartItem=cartRepo.findByProductIdandCustId(custId,productId).orElseThrow(()->new CartItemNotFoundException(productId));
		cartRepo.deleteByProductIdandCustId(custId,productId);
		return existingCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long quantity,Long custId,Long productId) throws CartItemNotFoundException {
		CartItem existingCartItem=cartRepo.findByProductIdandCustId(custId,productId).orElseThrow(()->new CartItemNotFoundException(productId));
		existingCartItem.setQuantity(existingCartItem.getQuantity()+quantity);
		return cartRepo.save(existingCartItem);
	}	
	
}
