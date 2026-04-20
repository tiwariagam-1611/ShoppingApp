package com.shoppingapp.dto.response;

public class NotificationResponse {
	
	private Long notificationId;
	private String message;
	private String notificationType;
	private String status;
	private String recipientReference;
	
	public NotificationResponse(Long notificationId, String message, String notificationType, String status, String recipientReference) {
		
		
		this.notificationId = notificationId;
		this.message = message;
		this.notificationType = notificationType;
		this.status = status;
		this.recipientReference = recipientReference;
		
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public String getMessage() {
		return message;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public String getStatus() {
		return status;
	}

	public String getRecipientReference() {
		return recipientReference;
	}
	

}