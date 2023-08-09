package com.project.foodapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.exceptions.ProductRestaurantNotFoundException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.model.Product;
import com.project.foodapp.model.Restaurant;
import com.project.foodapp.model.RestaurantDTO;
import com.project.foodapp.service.ProductService;
import com.project.foodapp.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Restaurant>> getAllRestaurants(){
		return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
	}
	
	@GetMapping("/{restId}")
	public ResponseEntity<Restaurant> fetchRestaurantById(@PathVariable("restId") Long restId) throws RestaurantNotFoundException {
		Restaurant restaurant = restaurantService.fetchRestaurant(restId);
		return ResponseEntity.ok().body(restaurant);
	}
	
	@PutMapping("/{restId}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("restId") Long restId,@RequestBody @Valid RestaurantDTO restaurant,BindingResult bindingResult) throws RestaurantNotFoundException, IllegalArgumentException{
		Restaurant existingRestaurant = restaurantService.updateRestaurant(restId,restaurant,bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
	}
	
	@DeleteMapping("/{restId}")
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("restId") Long restId) throws RestaurantNotFoundException{
		Restaurant existingRestaurant = restaurantService.deleteRestaurant(restId);
		return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.ok().body(productService.getAllProducts());
	}
	
	@GetMapping("/{restId}/products")
	public ResponseEntity<List<Product>> fetchProductsByRestaurantId(@PathVariable("restId") Long restId) throws ProductNotFoundException {
		List<Product> products = productService.findProductByRestId(restId);
		return ResponseEntity.ok().body(products);
	}
	
	@PostMapping("/{restId}/product")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product,BindingResult bindingResult,@PathVariable("restId") Long restId) throws IllegalArgumentException, RestaurantNotFoundException{
		return ResponseEntity.ok(productService.createProduct(product, bindingResult, restId));
	}
	
	@GetMapping("/{restId}/product/{productId}")
	public ResponseEntity<Product> fetchProductId(@PathVariable("productId") Long productId,@PathVariable("restId") Long restId) throws ProductNotFoundException, RestaurantNotFoundException {
		Product product = productService.fetchProduct(productId, restId);
		return ResponseEntity.ok().body(product);
	}
	
	@PutMapping("/{restId}/product/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long productId,@PathVariable("restId") Long restId,@RequestBody @Valid Product product,BindingResult bindingResult) throws RestaurantNotFoundException, IllegalArgumentException, ProductNotFoundException, ProductRestaurantNotFoundException{
		Product existingProduct = productService.updateProduct(productId, restId, product, bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingProduct);
	}
	
	@DeleteMapping("/{restId}/product/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Long productId,@PathVariable("restId") Long restId) throws ProductNotFoundException, RestaurantNotFoundException, ProductRestaurantNotFoundException{
		Product existingProduct = productService.deleteProduct(productId,restId);
		return ResponseEntity.status(HttpStatus.OK).body(existingProduct);
	}
}
