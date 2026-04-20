package com.shoppingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")


public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notficationId;
	private String notificationType;
	private String recipientReference;
	private String message;
	private String status;
	
	
	public Notification() {
		
	}
	public Notification(String notificationType, String recipientRefernce, String message , String status) {
		
		this.notificationType = notificationType;
		this.recipientReference = recipientRefernce;
		this.message = message;
		this.status = status;
		
	}
	public Long getNotificationId() {
		return notficationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notficationId = notificationId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getRecipientReference() {
		return recipientReference;
	}
	public void setRecipientReference(String recipientReference) {
		this.recipientReference = recipientReference;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}