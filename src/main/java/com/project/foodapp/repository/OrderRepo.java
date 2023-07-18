package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.ProductOrder;

public interface OrderRepo extends JpaRepository<ProductOrder, Long> {

}
