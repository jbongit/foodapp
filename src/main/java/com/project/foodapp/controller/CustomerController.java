package com.project.foodapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.Product;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.ProductService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return ResponseEntity.ok().body(customerService.getAllCustomers());
	}
	
	@PostMapping
	public ResponseEntity<Customer> createCustomerProfile(@RequestBody Customer customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> fetchCustomerById(@PathVariable("id") Long id) throws CustomerNotFoundException {
		Customer customer = customerService.fetchCustomer(id);
		return ResponseEntity.ok().body(customer);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,@RequestBody Customer customer) throws CustomerNotFoundException{
		Customer existingCustomer = customerService.updateCustomer(id,customer);
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
	
	@PostMapping("/{custId}/product/{productId}/addToCart")
	public ResponseEntity<CartItem> addToCart(@RequestParam("quantity") Long quantity,@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws ProductNotFoundException, CartItemNotFoundException{
		return ResponseEntity.ok().body(customerService.addToCart(quantity,custId,productId));
	}
	
	@DeleteMapping("/{custId}/product/{productId}/remove")
	public ResponseEntity<CartItem> removeFromCartById(@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws CartItemNotFoundException{
		return ResponseEntity.ok().body(customerService.removeFromCartByProductId(custId, productId));
	}
	
	
}
