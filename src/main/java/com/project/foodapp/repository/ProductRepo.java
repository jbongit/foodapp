package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}
