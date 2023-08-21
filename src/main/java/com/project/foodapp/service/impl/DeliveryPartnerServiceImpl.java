package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.foodapp.exceptions.DeliveryPartnerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.model.Address;
import com.project.foodapp.model.DeliveryPartner;
import com.project.foodapp.model.DeliveryPartnerDTO;
import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.repository.CustomerRepo;
import com.project.foodapp.repository.DeliveryPartnerRepo;
import com.project.foodapp.repository.RestaurantRepo;
import com.project.foodapp.service.DeliveryPartnerService;

import jakarta.validation.Valid;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

	@Autowired
	DeliveryPartnerRepo deliveryPartnerRepo;
	
	@Autowired
	RestaurantRepo restaurantRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public DeliveryPartner createDeliveryPartner(@Valid DeliveryPartnerDTO deliveryPartnerDTO, BindingResult bindingResult)
			throws IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		
		if (customerRepo.findByCustEmailId(deliveryPartnerDTO.getDpEmailId())!=null || restaurantRepo.findByRestEmailId(deliveryPartnerDTO.getDpEmailId())!=null || deliveryPartnerRepo.findByDpEmailId(deliveryPartnerDTO.getDpEmailId())!=null) {
			throw new IllegalArgumentException("DeliveryPartner Email Id Already Exist");
		}

		Address address = Address.builder().houseNo(deliveryPartnerDTO.getHouseNo()).area(deliveryPartnerDTO.getArea())
				.city(deliveryPartnerDTO.getCity()).state(deliveryPartnerDTO.getState()).pincode(deliveryPartnerDTO.getPincode())
				.latitude(deliveryPartnerDTO.getLatitude()).longitude(deliveryPartnerDTO.getLongitude()).build();

		DeliveryPartner deliveryPartner = DeliveryPartner.builder().dpName(deliveryPartnerDTO.getDpName())
				.dpEmailId(deliveryPartnerDTO.getDpEmailId()).dpMobileno(deliveryPartnerDTO.getDpMobileno())
				.dpPassword(passwordEncoder.encode(deliveryPartnerDTO.getDpPassword())).dpAddress(address).role("DeliveryPartner").build();

		return deliveryPartnerRepo.save(deliveryPartner);
	}

	@Override
	public DeliveryPartner fetchDeliveryPartner(Long id) throws DeliveryPartnerNotFoundException {
		DeliveryPartner deliveryPartner = deliveryPartnerRepo.findById(id).orElseThrow(() -> new DeliveryPartnerNotFoundException(id));
		return deliveryPartner;
	}

	@Override
	public DeliveryPartner updateDeliveryPartner(Long id,DeliveryPartnerDTO updatedDeliveryPartner,BindingResult bindingResult) throws DeliveryPartnerNotFoundException, IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		DeliveryPartner existingDeliveryPartner = deliveryPartnerRepo.findById(id)
				.orElseThrow(() -> new DeliveryPartnerNotFoundException(id));
		
		existingDeliveryPartner.setDpName(updatedDeliveryPartner.getDpName());
		
		Address address = existingDeliveryPartner.getDpAddress();
		
		address.setHouseNo(updatedDeliveryPartner.getHouseNo());
		address.setArea(updatedDeliveryPartner.getArea());
		address.setCity(updatedDeliveryPartner.getCity());
		address.setState(updatedDeliveryPartner.getState());
		address.setPincode(updatedDeliveryPartner.getPincode());
		
		existingDeliveryPartner.setDpAddress(address);
		existingDeliveryPartner.setDpMobileno(updatedDeliveryPartner.getDpMobileno());
		return deliveryPartnerRepo.save(existingDeliveryPartner);
	}

	@Override
	public DeliveryPartner deleteDeliveryPartner(Long id) throws DeliveryPartnerNotFoundException {
		DeliveryPartner existingDeliveryPartner=deliveryPartnerRepo.findById(id).orElseThrow(()->new DeliveryPartnerNotFoundException(id));
		deliveryPartnerRepo.deleteById(id);
		return existingDeliveryPartner;
	}

	@Override
	public List<DeliveryPartner> getAllDeliveryPartners() {
		return deliveryPartnerRepo.findAll();
	}
	
	@Override
	public RegistrationDTO findByEmailId(String emailId){
		DeliveryPartner dp=deliveryPartnerRepo.findByDpEmailId(emailId);
		if(dp!=null) {
			RegistrationDTO user=RegistrationDTO.builder().userId(dp.getDpId()).userName(dp.getDpName()).userEmailId(dp.getDpEmailId()).userMobileno(dp.getDpMobileno()).
					userPassword(dp.getDpPassword()).houseNo(dp.getDpAddress().getHouseNo()).area(dp.getDpAddress().getArea()).role(dp.getRole()).build();
			return user;
		}
		return null;
	}
}
