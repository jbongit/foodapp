package com.project.foodapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.CreatePayment;
import com.project.foodapp.service.PaymentStripeService;
import com.stripe.exception.StripeException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PaymentController {

	@Autowired
	private PaymentStripeService paymentStripeService;

	@Value("${stripe.apikey}")
	private String stripeApiKey;

	@PostMapping("/create-payment-intent")
	@ResponseBody
	public String createPaymentIntent(@RequestBody CreatePayment requestBody) throws StripeException {
		return paymentStripeService.createPaymentIntent(requestBody);
		
	}

	@GetMapping("/checkout")
	public String showCheckOutPage(Model model) {
		List<CartItem> cartItem=paymentStripeService.checkoutAddCartItems();
		model.addAttribute("cartItems",cartItem.toArray());
		System.out.println(cartItem);
		return "checkout";
	}

	@GetMapping("/processPayment")
	public void processPayment(@RequestParam("custId") Long custId,@RequestParam("redirect_status") String paymentStatus,HttpServletResponse response) throws IOException, CartItemNotFoundException, CustomerNotFoundException {
		paymentStripeService.processPayment(paymentStatus,custId);
		response.sendRedirect("http://localhost:3000/success");
	}

	@GetMapping("/success")
	public String success() {
		return "success";
	}
// Define other endpoints and methods as needed
}