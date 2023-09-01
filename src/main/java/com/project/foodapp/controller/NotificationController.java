package com.project.foodapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.payloads.MessageRequest;
import com.project.foodapp.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/all")
	public void sendNotificationToAll(@RequestBody MessageRequest request) {
		notificationService.sendNotificationToAll(request.message());
	}
	
	@PostMapping("/customer/{custId}")
	public void sendNotificationToCustomer(@PathVariable("custId") Long custId,@RequestBody MessageRequest request) throws CustomerNotFoundException {
		notificationService.sendNotificationToCustomer(custId,request.message());
	}
}
