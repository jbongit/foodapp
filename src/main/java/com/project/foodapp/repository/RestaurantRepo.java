package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

	Restaurant findByRestEmailId(String restEmailId);

}
