package com.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingapp.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByRecipientReference(String recipientReference);
	
}
