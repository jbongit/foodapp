package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.RestaurantNotFoundException;
import com.project.foodapp.model.Address;
import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.model.Restaurant;
import com.project.foodapp.model.RestaurantDTO;
import com.project.foodapp.repository.CustomerRepo;
import com.project.foodapp.repository.DeliveryPartnerRepo;
import com.project.foodapp.repository.RestaurantRepo;
import com.project.foodapp.service.RestaurantService;

import jakarta.validation.Valid;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepo restaurantRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	DeliveryPartnerRepo deliveryPartnerRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Restaurant createRestaurant(@Valid RestaurantDTO restaurantDTO, BindingResult bindingResult)
			throws IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}

		if (customerRepo.findByCustEmailId(restaurantDTO.getRestEmailId())!=null || restaurantRepo.findByRestEmailId(restaurantDTO.getRestEmailId())!=null || deliveryPartnerRepo.findByDpEmailId(restaurantDTO.getRestEmailId())!=null) {
			throw new IllegalArgumentException("Restaurant Email Id Already Exist");
		}

		Address address = Address.builder().houseNo(restaurantDTO.getHouseNo()).area(restaurantDTO.getArea())
				.city(restaurantDTO.getCity()).state(restaurantDTO.getState()).pincode(restaurantDTO.getPincode())
				.latitude(restaurantDTO.getLatitude()).longitude(restaurantDTO.getLongitude()).build();

		Restaurant restaurant = Restaurant.builder().restName(restaurantDTO.getRestName())
				.restEmailId(restaurantDTO.getRestEmailId()).restMobileno(restaurantDTO.getRestMobileno())
				.restPassword(passwordEncoder.encode(restaurantDTO.getRestPassword())).restAddress(address).role("Restaurant").build();

		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant fetchRestaurant(Long id) throws RestaurantNotFoundException {
		Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
		return restaurant;
	}

	@Override
	public Restaurant updateRestaurant(Long id,RestaurantDTO updatedRestaurant,BindingResult bindingResult) throws RestaurantNotFoundException, IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		Restaurant existingRestaurant = restaurantRepo.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
		
		existingRestaurant.setRestName(updatedRestaurant.getRestName());
		Address address = existingRestaurant.getRestAddress();
		
		address.setHouseNo(updatedRestaurant.getHouseNo());
		address.setArea(updatedRestaurant.getArea());
		address.setCity(updatedRestaurant.getCity());
		address.setState(updatedRestaurant.getState());
		address.setPincode(updatedRestaurant.getPincode());
		
		existingRestaurant.setRestAddress(address);
		existingRestaurant.setRestMobileno(updatedRestaurant.getRestMobileno());
		return restaurantRepo.save(existingRestaurant);
	}

	@Override
	public Restaurant deleteRestaurant(Long id) throws RestaurantNotFoundException {
		Restaurant existingRestaurant=restaurantRepo.findById(id).orElseThrow(()->new RestaurantNotFoundException(id));
		restaurantRepo.deleteById(id);
		return existingRestaurant;
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepo.findAll();
	}
	
	@Override
	public RegistrationDTO findByEmailId(String emailId){
		Restaurant restaurant=restaurantRepo.findByRestEmailId(emailId);
		if(restaurant!=null) {
			RegistrationDTO user=RegistrationDTO.builder().userId(restaurant.getRestId()).userName(restaurant.getRestName()).userEmailId(restaurant.getRestEmailId()).userMobileno(restaurant.getRestMobileno()).
					userPassword(restaurant.getRestPassword()).houseNo(restaurant.getRestAddress().getHouseNo()).area(restaurant.getRestAddress().getArea()).role(restaurant.getRole()).build();
			return user;
		}
		return null;
	}

}
