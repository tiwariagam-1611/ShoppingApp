package com.shoppingapp.service;

import com.shoppingapp.dto.request.NotificationRequest;
import com.shoppingapp.dto.response.NotificationResponse;
import com.shoppingapp.model.Notification;
import com.shoppingapp.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void createNotification_shouldPersistAndReturnDto() {
        NotificationRequest request = new NotificationRequest();
        request.setNotificationType("LOW_STOCK");
        request.setRecipientReference("ADMIN");
        request.setMessage("Low stock alert");
        request.setStatus("NEW");

        Notification saved = new Notification();
        saved.setNotificationId(10L);
        saved.setNotificationType("LOW_STOCK");
        saved.setRecipientReference("ADMIN");
        saved.setMessage("Low stock alert");
        saved.setStatus("NEW");

        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        NotificationResponse response = notificationService.createNotification(request);

        assertEquals(10L, response.getNotificationId());
        assertEquals("LOW_STOCK", response.getNotificationType());
    }

    @Test
    void getNotificationById_whenMissing_shouldThrow() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> notificationService.getNotificationById(1L));
    }

    @Test
    void checkForLowStock_whenAboveThreshold_shouldNotSave() {
        notificationService.checkForLowStock(10L, 8);

        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void generateCheckoutConfirmation_shouldSaveNotification() {
        notificationService.generateCheckoutConfirmation(7L, 100L);

        verify(notificationRepository).save(any(Notification.class));
    }
}
