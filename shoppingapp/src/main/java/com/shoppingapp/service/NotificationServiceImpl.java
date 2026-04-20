package com.shoppingapp.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingapp.dto.request.NotificationRequest;
import com.shoppingapp.dto.response.NotificationResponse;
import com.shoppingapp.model.Notification;
import com.shoppingapp.repository.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private  final NotificationRepository notificationRepository;
	
@Autowired
public NotificationServiceImpl(NotificationRepository notificationRepository) {
	this.notificationRepository = notificationRepository;
}
	
	private NotificationResponse mapToResponse(Notification notification) {
		
		return new NotificationResponse(
				notification.getNotificationId(),
				notification.getMessage(),
				notification.getNotificationType(),
				notification.getStatus(),
				notification.getRecipientReference()
				);
	
				
	}
	
	@Override
	public NotificationResponse createNotification(NotificationRequest request) {
		Notification notification = new Notification();
		notification.setNotificationType(request.getNotificationType());
		notification.setMessage(request.getMessage());
		notification.setRecipientReference(request.getRecipientReference());
		notification.setStatus(request.getStatus());
		
		Notification savedNotification = notificationRepository.save(notification);
		return mapToResponse(savedNotification);
	}
	
	@Override
	public List<NotificationResponse> getAllNotifications(){
		return notificationRepository.findAll().stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
		
	}
	@Override
	public NotificationResponse getNotificationById(Long Id) {
		Notification notification = notificationRepository.findById(Id)
		 .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + Id));
	        return mapToResponse(notification);
		
	}
	@Override
	public List< NotificationResponse> getNotificationByRecipient(String recipientReference ){
		return notificationRepository.findByRecipientReference(recipientReference).stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
		
	}
	
	@Override
	public void generateCheckoutConfirmation(Long userId,Long orderId) {
		
	Notification notification = new Notification();
		notification.setMessage(String.format("Checkout confirmed for order %d.", orderId));
		notification.setNotificationType("CHECKOUT_CONFIRMATION");
	notification.setRecipientReference(String.valueOf(userId));
	notification.setStatus("NEW");
		notificationRepository.save(notification);
		
	}
	
	@Override
	public void checkForLowStock(Long productId, Integer currentStock) {

		   final int REORDER_LEVEL_THRESHOLD = 5; 

	        if (currentStock <= REORDER_LEVEL_THRESHOLD) {
	             String message = String.format("ALERT: Product ID %d is low on stock. Current quantity: %d.", productId, currentStock);
	            
	            Notification notification = new Notification();
	            notification.setNotificationType("LOW_STOCK");
	           
	            notification.setRecipientReference("ADMIN_USER"); 
	            notification.setMessage(message);
	            notification.setStatus("NEW");

	            notificationRepository.save(notification);
	        }
	    }
		
		
		
	}
	
	