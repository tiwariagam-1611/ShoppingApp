package com.shoppingapp.service;

import com.shoppingapp.dto.request.NotificationRequest;
import com.shoppingapp.dto.response.NotificationResponse;
import com.shoppingapp.model.Notification;
import com.shoppingapp.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NotificationServiceImplTest {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification saveNotification(String type, String recipient, String message) {
        Notification notification = new Notification();
        notification.setNotificationType(type);
        notification.setRecipientReference(recipient);
        notification.setMessage(message);
        notification.setStatus("NEW");
        return notificationRepository.save(notification);
    }

    @Test
    void createNotification_shouldPersistAndReturnDto() {
        NotificationRequest request = new NotificationRequest();
        request.setNotificationType("LOW_STOCK");
        request.setRecipientReference("ADMIN");
        request.setMessage("Low stock alert");
        request.setStatus("NEW");

        NotificationResponse response = notificationService.createNotification(request);

        assertEquals("LOW_STOCK", response.getNotificationType());
        assertEquals("ADMIN", response.getRecipientReference());
    }

    @Test
    void getNotificationById_whenMissing_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () -> notificationService.getNotificationById(1L));
    }

    @Test
    void getAllNotifications_shouldReturnMappedList() {
        saveNotification("CHECKOUT_CONFIRMATION", "7", "Checkout confirmed");

        List<NotificationResponse> response = notificationService.getAllNotifications();

        assertEquals(1, response.size());
        assertEquals("CHECKOUT_CONFIRMATION", response.get(0).getNotificationType());
    }

    @Test
    void getNotificationByRecipient_shouldReturnMappedList() {
        saveNotification("LOW_STOCK", "ADMIN_USER", "Low stock");

        List<NotificationResponse> response = notificationService.getNotificationByRecipient("ADMIN_USER");

        assertEquals(1, response.size());
        assertEquals("LOW_STOCK", response.get(0).getNotificationType());
    }

    @Test
    void checkForLowStock_whenAboveThreshold_shouldNotSave() {
        long before = notificationRepository.count();

        notificationService.checkForLowStock(10L, 8);

        assertEquals(before, notificationRepository.count());
    }

    @Test
    void checkForLowStock_whenAtThreshold_shouldSaveLowStockNotification() {
        notificationService.checkForLowStock(10L, 5);

        List<Notification> saved = notificationRepository.findAll();
        assertEquals(1, saved.size());
        assertEquals("LOW_STOCK", saved.get(0).getNotificationType());
        assertEquals("ADMIN_USER", saved.get(0).getRecipientReference());
    }

    @Test
    void generateCheckoutConfirmation_shouldSaveNotification() {
        notificationService.generateCheckoutConfirmation(7L, 100L);

        List<Notification> saved = notificationRepository.findAll();
        assertEquals(1, saved.size());
        assertEquals("CHECKOUT_CONFIRMATION", saved.get(0).getNotificationType());
    }
}
