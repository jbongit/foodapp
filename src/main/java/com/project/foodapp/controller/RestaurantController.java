package com.project.foodapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.model.Restaurant;
import com.project.foodapp.model.RestaurantDTO;
import com.project.foodapp.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	RestaurantService restaurantService;
	
	@GetMapping
	public ResponseEntity<List<Restaurant>> getAllRestaurants(){
		return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> fetchRestaurantById(@PathVariable("id") Long id) throws RestaurantNotFoundException {
		Restaurant restaurant = restaurantService.fetchRestaurant(id);
		return ResponseEntity.ok().body(restaurant);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") Long id,@RequestBody @Valid RestaurantDTO restaurant,BindingResult bindingResult) throws RestaurantNotFoundException, IllegalArgumentException{
		Restaurant existingRestaurant = restaurantService.updateRestaurant(id,restaurant,bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") Long id) throws RestaurantNotFoundException{
		Restaurant existingRestaurant = restaurantService.deleteRestaurant(id);
		return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
	}
}
