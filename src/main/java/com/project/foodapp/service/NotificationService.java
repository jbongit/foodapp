package com.project.foodapp.service;

import com.project.foodapp.exceptions.CustomerNotFoundException;

public interface NotificationService {
	public void sendNotificationToAll(String message);
	public void sendNotificationToCustomer(Long custId,String message) throws CustomerNotFoundException;
}
