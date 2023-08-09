package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.CreatePayment;
import com.stripe.exception.StripeException;

public interface PaymentStripeService {

	String createPaymentIntent(CreatePayment requestBody) throws StripeException;
	
	void processPayment(String paymentStatus);

	List<CartItem> checkoutAddCartItems();
}
