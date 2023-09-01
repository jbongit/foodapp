package com.project.foodapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.model.Customer;
import com.project.foodapp.service.CustomerService;
import com.project.foodapp.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void sendNotificationToAll(String message) {
		kafkaTemplate.send("notification-topic", message);
	}
	
	@Override
	public void sendNotificationToCustomer(Long custId, String message) throws CustomerNotFoundException {
		Customer cust=customerService.fetchCustomer(custId);
		kafkaTemplate.send("user-topic", cust.getCustEmailId()+":"+message);
	}

}
