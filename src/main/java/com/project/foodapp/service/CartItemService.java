package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;

public interface CartItemService {
	public List<CartItem> getCartItemsByCustId(Long custId) throws CartItemNotFoundException;
	public CartItem addToCart(Long quantity,Long custId,Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException;
	public CartItem removeFromCartByProductId(Long custId, Long productId) throws CartItemNotFoundException;
	public CartItem updateCartItemQuantity(Long quantity, Long custId, Long productId) throws CartItemNotFoundException;
}
