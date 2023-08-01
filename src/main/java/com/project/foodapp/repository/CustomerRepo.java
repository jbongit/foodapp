package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

	Customer findByCustEmailId(String custEmailId);

}
