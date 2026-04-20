package com.shoppingapp.service;

import com.shoppingapp.dto.response.NotificationResponse;
import com.shoppingapp.dto.request.NotificationRequest;
import java.util.List;

public interface NotificationService {
	
	NotificationResponse createNotification(NotificationRequest notificationRequest);
	List<NotificationResponse> getAllNotifications();
	NotificationResponse getNotificationById(Long Id);
	List<NotificationResponse> getNotificationByRecipient(String RecipientReference);
	
    void generateCheckoutConfirmation(Long userId, Long orderId); 
    void checkForLowStock(Long productId, Integer currentStock); 

	
	

}