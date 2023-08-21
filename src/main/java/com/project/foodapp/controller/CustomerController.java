package com.project.foodapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import com.project.foodapp.service.CartItemService;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CartItemService cartItemService;
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return ResponseEntity.ok().body(customerService.getAllCustomers());
	}
	
	@GetMapping("/{custId}")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<Customer> fetchCustomerById(@PathVariable("custId") Long custId) throws CustomerNotFoundException {
		Customer customer = customerService.fetchCustomer(custId);
		return ResponseEntity.ok().body(customer);
	}
	
	@PutMapping("/{custId}")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("custId") Long custId,@RequestBody @Valid CustomerDTO customer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException{
		Customer existingCustomer = customerService.updateCustomer(custId,customer,bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingCustomer);
	}
	
	@DeleteMapping("/{custId}")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("custId") Long custId) throws CustomerNotFoundException{
		Customer existingCustomer = customerService.deleteCustomer(custId);
		return ResponseEntity.status(HttpStatus.OK).body(existingCustomer);
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{custId}/cartItems")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("custId") Long custId) throws CartItemNotFoundException{
		return ResponseEntity.ok().body(cartItemService.getCartItemsByCustId(custId));
	}
	
	@PostMapping("/{custId}/product/{productId}/addToCart")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<CartItem> addToCart(@RequestParam("quantity") Long quantity,@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws ProductNotFoundException, CartItemNotFoundException, CustomerNotFoundException{
		return ResponseEntity.ok().body(cartItemService.addToCart(quantity,custId,productId));
	}
	
	@DeleteMapping("/{custId}/product/{productId}/remove")
	@PreAuthorize("hasAuthority('Customer') and #custId==authentication.principal.id")
	public ResponseEntity<CartItem> removeFromCartById(@PathVariable("custId") Long custId,@PathVariable("productId") Long productId) throws CartItemNotFoundException{
		return ResponseEntity.ok().body(cartItemService.removeFromCartByProductId(custId, productId));
	}
	
}
