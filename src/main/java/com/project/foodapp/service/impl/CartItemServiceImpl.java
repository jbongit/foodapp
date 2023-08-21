package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Product;
import com.project.foodapp.repository.CartRepo;
import com.project.foodapp.repository.ProductRepo;
import com.project.foodapp.service.CartItemService;
import com.project.foodapp.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException {
		if(customerService.fetchCustomer(custId)==null) {
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
