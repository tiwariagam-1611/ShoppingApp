package com.shoppingapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.dto.request.NotificationRequest;
import com.shoppingapp.dto.response.NotificationResponse;
import com.shoppingapp.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {
        logger.info("Received request to create a new notification of type: {}", request.getNotificationType());
        NotificationResponse response = notificationService.createNotification(request);
        logger.info("Notification created successfully with ID: {}", response.getNotificationId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/notifications
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        logger.info("Fetching all notifications.");
        List<NotificationResponse> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // GET /api/notifications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        logger.info("Fetching notification details for ID: {}", id);
        NotificationResponse notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    // GET /api/notifications/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByRecipient(@PathVariable String userId) {
        logger.info("Fetching notifications for recipient: {}", userId);
        List<NotificationResponse> notifications = notificationService.getNotificationByRecipient(userId);
        return ResponseEntity.ok(notifications);
    }
}