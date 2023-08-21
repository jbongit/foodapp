package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
