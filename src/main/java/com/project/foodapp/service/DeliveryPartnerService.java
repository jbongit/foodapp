package com.project.foodapp.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.exceptions.DeliveryPartnerNotFoundException;
import com.project.foodapp.model.DeliveryPartner;
import com.project.foodapp.model.DeliveryPartnerDTO;

import jakarta.validation.Valid;

public interface DeliveryPartnerService {
	public DeliveryPartner createDeliveryPartner(@Valid DeliveryPartnerDTO restaurant, BindingResult bindingResult) throws IllegalArgumentException;
	public DeliveryPartner fetchDeliveryPartner(Long id) throws DeliveryPartnerNotFoundException;
	public DeliveryPartner updateDeliveryPartner(Long id,DeliveryPartnerDTO updatedDeliveryPartner,BindingResult bindingResult) throws DeliveryPartnerNotFoundException, IllegalArgumentException;
	public DeliveryPartner deleteDeliveryPartner(Long id) throws DeliveryPartnerNotFoundException;
	public List<DeliveryPartner> getAllDeliveryPartners();
}
