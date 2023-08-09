package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.Address;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;
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
	public Customer createCustomer(CustomerDTO customerDTO,BindingResult bindingResult) throws IllegalArgumentException {
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage="";
			for (FieldError error : errors) {
				errorMessage+=error.getDefaultMessage()+"\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		
		Customer existingCustomer=customerRepo.findByCustEmailId(customerDTO.getCustEmailId());
		
		if(existingCustomer!=null) {
			throw new IllegalArgumentException("Customer Email Id Already Exist");
		}
		
		Address address=Address.builder().houseNo(customerDTO.getHouseNo()).area(customerDTO.getArea()).city(customerDTO.getCity()).state(customerDTO.getState()).pincode(customerDTO.getPincode()).latitude(customerDTO.getLatitude()).longitude(customerDTO.getLongitude()).build();
		
		Customer customer=Customer.builder().custName(customerDTO.getCustName()).custEmailId(customerDTO.getCustEmailId()).custMobileno(customerDTO.getCustMobileno()).custPassword(customerDTO.getCustPassword()).custAddress(address).role("Customer").build();
		
		return customerRepo.save(customer);
	}

	@Override
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException{
		Customer customer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		return customer;
	}

	@Override
	public Customer updateCustomer(Long id,CustomerDTO updatedCustomer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		Customer existingCustomer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(id));
		
		existingCustomer.setCustName(updatedCustomer.getCustName());
		existingCustomer.setCustEmailId(updatedCustomer.getCustEmailId());
		Address address = existingCustomer.getCustAddress();
		
		address.setHouseNo(updatedCustomer.getHouseNo());
		address.setArea(updatedCustomer.getArea());
		address.setCity(updatedCustomer.getCity());
		address.setState(updatedCustomer.getState());
		address.setPincode(updatedCustomer.getPincode());
		
		existingCustomer.setCustAddress(address);
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
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException {
		if(fetchCustomer(custId)==null) {
			throw new CustomerNotFoundException(custId);
		}
		
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

	@Override
	public List<CartItem> getCartItemsByCustId(Long custId) throws CartItemNotFoundException {
		return cartRepo.findByCustId(custId);
	}	
	
}
