package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodapp.model.Product;
import com.project.foodapp.repository.ProductRepo;
import com.project.foodapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepo productRepo;
	
	@Override
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
}
