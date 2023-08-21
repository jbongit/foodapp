package com.project.foodapp.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.model.Restaurant;
import com.project.foodapp.model.RestaurantDTO;

import jakarta.validation.Valid;

public interface RestaurantService {
	public Restaurant createRestaurant(@Valid RestaurantDTO restaurant, BindingResult bindingResult) throws IllegalArgumentException;
	public Restaurant fetchRestaurant(Long id) throws RestaurantNotFoundException;
	public Restaurant updateRestaurant(Long id,RestaurantDTO updatedRestaurant,BindingResult bindingResult) throws RestaurantNotFoundException, IllegalArgumentException;
	public Restaurant deleteRestaurant(Long id) throws RestaurantNotFoundException;
	public List<Restaurant> getAllRestaurants();
	RegistrationDTO findByEmailId(String emailId);
}
