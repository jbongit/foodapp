package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.CreatePayment;
import com.stripe.exception.StripeException;

public interface PaymentStripeService {

	String createPaymentIntent(CreatePayment requestBody) throws StripeException;
	
	List<CartItem> checkoutAddCartItems();

	void processPayment(String paymentStatus, Long custId) throws CartItemNotFoundException, CustomerNotFoundException;
}
