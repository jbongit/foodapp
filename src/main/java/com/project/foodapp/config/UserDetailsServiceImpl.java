package com.project.foodapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.DeliveryPartnerService;
import com.project.foodapp.service.RestaurantService;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private DeliveryPartnerService deliveryPartnerService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegistrationDTO user=null;
		
		if(customerService.findByEmailId(username)!=null) {
			user=customerService.findByEmailId(username);
		}else if(restaurantService.findByEmailId(username)!=null) {
			user=restaurantService.findByEmailId(username);
		}else if(deliveryPartnerService.findByEmailId(username)!=null){
			user=deliveryPartnerService.findByEmailId(username);
		}
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found user with Email Id : "+username);
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
