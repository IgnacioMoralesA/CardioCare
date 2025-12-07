package cl.ufro.dci.cardiocare.notification.service;

import cl.ufro.dci.cardiocare.notification.dto.*;

import java.util.List;

public interface NotificationService {
    NotificationResponse send(NotificationRequest request);
    List<NotificationResponse> getByUser(Long userId);
    void markAsRead(Long id);
}
