package com.project.foodapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;
import com.project.foodapp.model.Product;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
@CrossOrigin("http://localhost:3000")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return ResponseEntity.ok().body(customerService.getAllCustomers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> fetchCustomerById(@PathVariable("id") Long id) throws CustomerNotFoundException {
		Customer customer = customerService.fetchCustomer(id);
		return ResponseEntity.ok().body(customer);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,@RequestBody @Valid CustomerDTO customer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException{
		Customer existingCustomer = customerService.updateCustomer(id,customer,bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingCustomer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id) throws CustomerNotFoundException{
		Customer existingCustomer = customerService.deleteCustomer(id);
		return ResponseEntity.status(HttpStatus.OK).body(existingCustomer);
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{custId}/cartItems")
	public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("custId") Long custId) throws CartItemNotFoundException{
		return ResponseEntity.ok().body(customerService.getCartItemsByCustId(custId));
	}
	
	@PostMapping("/{custId}/product/{productId}/addToCart")
	public ResponseEntity<CartItem> addToCart(@RequestParam("quantity") Long quantity,@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException{
		return ResponseEntity.ok().body(customerService.addToCart(quantity,custId,productId));
	}
	
	@DeleteMapping("/{custId}/product/{productId}/remove")
	public ResponseEntity<CartItem> removeFromCartById(@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws CartItemNotFoundException{
		return ResponseEntity.ok().body(customerService.removeFromCartByProductId(custId, productId));
	}
	
}
