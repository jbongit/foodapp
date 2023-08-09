package com.project.foodapp.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.exceptions.ProductRestaurantNotFoundException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.model.Product;


import jakarta.validation.Valid;

public interface ProductService {

	List<Product> getAllProducts();
	Product createProduct(@Valid Product product, BindingResult bindingResult,Long restId) throws IllegalArgumentException, RestaurantNotFoundException;
	Product fetchProduct(Long productId,Long restId) throws ProductNotFoundException, RestaurantNotFoundException;
	Product updateProduct(Long productId, Long restId,Product updatedProduct,BindingResult bindingResult) throws ProductNotFoundException, IllegalArgumentException, ProductRestaurantNotFoundException;
	List<Product> findProductByRestId(Long restId);
	Product deleteProduct(Long productId, Long restId) throws ProductNotFoundException, RestaurantNotFoundException, ProductRestaurantNotFoundException;

}
