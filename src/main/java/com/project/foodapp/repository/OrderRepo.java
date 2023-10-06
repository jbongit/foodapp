package com.project.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
	List<Order> findByCustId(Long custId);
}
