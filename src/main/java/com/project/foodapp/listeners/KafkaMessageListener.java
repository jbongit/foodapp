package com.project.foodapp.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.project.foodapp.payloads.Message;

@Component
public class KafkaMessageListener {

	@Autowired
    SimpMessagingTemplate simpMessagingTemplate;
	
	@KafkaListener(topics="notification-topic",groupId="notification-consumer-group")
	public void listen(@Payload String message) {
		System.out.println("Listener Received : "+message);
		Message messagePayload=new Message();
		messagePayload.setText(message);
		simpMessagingTemplate.convertAndSend("/all/messages", messagePayload);
	}
	
	@KafkaListener(topics="user-topic",groupId="notification-consumer-group")
	public void userListen(@Payload String message) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] parts=message.split(":",2);
		
		String custId="";
		String notification="";
		
		if(parts.length==2) {
			custId=parts[0];
			notification=parts[1];
		}
		
		System.out.println("Listener : "+custId+" Received : "+notification);
		
		Message messagePayload=new Message();
		messagePayload.setText(notification);
		simpMessagingTemplate.convertAndSendToUser(custId,"/specific", messagePayload);
	}
	
	
}
