package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{

}
