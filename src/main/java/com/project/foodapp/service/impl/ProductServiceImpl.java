package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.ProductNotFoundException;
import com.project.foodapp.exceptions.ProductRestaurantNotFoundException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.model.Product;
import com.project.foodapp.repository.CartRepo;
import com.project.foodapp.repository.ProductRepo;
import com.project.foodapp.repository.RestaurantRepo;
import com.project.foodapp.service.ProductService;

import jakarta.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	RestaurantRepo restaurantRepo;
	
	@Autowired 
	CartRepo cartRepo;
	
	@Override
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}

	@Override
	public Product createProduct(@Valid Product product, BindingResult bindingResult,Long restId) throws IllegalArgumentException, RestaurantNotFoundException {
		restaurantRepo.findById(restId).orElseThrow(()->new RestaurantNotFoundException(restId));
		product.setRestId(restId);
		return productRepo.save(product);
	}

	@Override
	public Product fetchProduct(Long productId,Long restId) throws ProductNotFoundException, RestaurantNotFoundException{
		restaurantRepo.findById(restId).orElseThrow(()->new RestaurantNotFoundException(restId));
		Product product=productRepo.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
		return product;
	}

	@Override
	public Product updateProduct(Long productId,Long restId,Product updatedProduct,BindingResult bindingResult) throws ProductNotFoundException, IllegalArgumentException, ProductRestaurantNotFoundException {
		if(productRepo.findByProductIdandRestId(productId,restId)==null) {
			throw  new ProductRestaurantNotFoundException(productId,restId);
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		Product existingProduct = productRepo.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));
		
		existingProduct.setProductName(updatedProduct.getProductName());
		existingProduct.setProductDesc(updatedProduct.getProductDesc());
		existingProduct.setProductCategory(updatedProduct.getProductCategory());
		existingProduct.setProductPrice(updatedProduct.getProductPrice());

		return productRepo.save(existingProduct);
	}

	@Override
	public Product deleteProduct(Long productId,Long restId) throws ProductNotFoundException,RestaurantNotFoundException, ProductRestaurantNotFoundException{
		if(productRepo.findByProductIdandRestId(productId,restId)==null) {
			throw  new ProductRestaurantNotFoundException(productId,restId);
		}
		cartRepo.deleteByProductId(productId);
		Product existingProduct=productRepo.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
		productRepo.deleteById(productId);
		return existingProduct;
	}

	@Override
	public List<Product> findProductByRestId(Long restId) {
		return productRepo.findAllByRestId(restId);
	}
}
