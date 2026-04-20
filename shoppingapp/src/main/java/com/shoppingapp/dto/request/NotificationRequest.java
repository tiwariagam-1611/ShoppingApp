package com.shoppingapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {

	@NotBlank(message = "Recipient reference cannot be null")
	private String recipientReference;
	@NotBlank(message = "Message cannot be null")
	private String message;
	@NotBlank(message = "Notification type cannot be null")
	private String notificationType;

	private String status = "New";

	public String getRecipientReference() {
		return recipientReference;
	}

	public void setRecipientReference(String recipientRefernce) {
		this.recipientReference = recipientRefernce;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}