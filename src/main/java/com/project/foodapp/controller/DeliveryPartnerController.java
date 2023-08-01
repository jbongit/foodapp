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
import com.project.foodapp.exceptions.DeliveryPartnerNotFoundException;
import com.project.foodapp.model.DeliveryPartner;
import com.project.foodapp.model.DeliveryPartnerDTO;
import com.project.foodapp.service.DeliveryPartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/deliveryPartner")
public class DeliveryPartnerController {
	@Autowired
	DeliveryPartnerService deliveryPartnerService;
	
	@GetMapping
	public ResponseEntity<List<DeliveryPartner>> getAllDeliveryPartners(){
		return ResponseEntity.ok().body(deliveryPartnerService.getAllDeliveryPartners());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeliveryPartner> fetchDeliveryPartnerById(@PathVariable("id") Long id) throws DeliveryPartnerNotFoundException {
		DeliveryPartner deliveryPartner = deliveryPartnerService.fetchDeliveryPartner(id);
		return ResponseEntity.ok().body(deliveryPartner);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DeliveryPartner> updateDeliveryPartner(@PathVariable("id") Long id,@RequestBody @Valid DeliveryPartnerDTO deliveryPartner,BindingResult bindingResult) throws DeliveryPartnerNotFoundException, IllegalArgumentException{
		DeliveryPartner existingDeliveryPartner = deliveryPartnerService.updateDeliveryPartner(id,deliveryPartner,bindingResult);
		return ResponseEntity.status(HttpStatus.OK).body(existingDeliveryPartner);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeliveryPartner> deleteDeliveryPartner(@PathVariable("id") Long id) throws DeliveryPartnerNotFoundException{
		DeliveryPartner existingDeliveryPartner = deliveryPartnerService.deleteDeliveryPartner(id);
		return ResponseEntity.status(HttpStatus.OK).body(existingDeliveryPartner);
	}
}
