package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.CreatePayment;
import com.project.foodapp.model.CreatePaymentResponse;
import com.project.foodapp.repository.CartRepo;
import com.project.foodapp.service.PaymentStripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentStripeServiceImpl implements PaymentStripeService{
	@Autowired
	private Gson gson;
	
	@Autowired
	private CartRepo cartRepo;

	
	@Value("${stripe.apikey}")
	private String stripeApiKey;
	
	@Override
	public String createPaymentIntent(CreatePayment requestBody) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		
//		List<CartItem> cartItems=cartRepo.findByCustId(custId);
//		CreatePayment c=new CreatePayment();
//		c.setItems(cartItems.to());
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
				.setAmount(calculateOrderAmount(requestBody.getItems()))
				.setCurrency("inr")
				.setDescription("Demo Description").setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
				.build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
		return gson.toJson(paymentResponse);
	}

	private long calculateOrderAmount(CartItem[] items) {
		long sum=0;
		for(CartItem c:items) {
			System.out.println(c);
			sum+=(Long.parseLong(c.getProduct().getProductPrice())*c.getQuantity());
		}
		return sum*100;
	}

	@Override
	public void processPayment(String paymentStatus) {
		if(paymentStatus.equals("succeeded")) {
			
			System.out.println("Payment Success");
		}else {
			System.out.println("Payment Failed");
		}
	}
	
	@Override
	public List<CartItem> checkoutAddCartItems() {
		return cartRepo.findByCustId(20L);
	}

}

