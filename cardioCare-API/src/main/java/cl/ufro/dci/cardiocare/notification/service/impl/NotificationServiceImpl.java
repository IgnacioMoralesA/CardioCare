package cl.ufro.dci.cardiocare.notification.service.impl;

import cl.ufro.dci.cardiocare.notification.dto.*;
import cl.ufro.dci.cardiocare.notification.domain.Notification;
import cl.ufro.dci.cardiocare.notification.repository.NotificationRepository;
import cl.ufro.dci.cardiocare.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;

    @Override
    public NotificationResponse send(NotificationRequest req) {
        Notification n = new Notification();
        n.setUserId(req.getUserId());
        n.setType(req.getType());
        n.setMessage(req.getMessage());
        n.setChannel(req.getChannel());
        n.setReferenceType(req.getReferenceType());
        n.setReferenceId(req.getReferenceId());
        n.setSentAt(Instant.now());
        n.setReadFlag(false);

        repo.save(n);
        return toResponse(n);
    }

    @Override
    public List<NotificationResponse> getByUser(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void markAsRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        n.setReadFlag(true);
        repo.save(n);
    }

    private NotificationResponse toResponse(Notification n) {
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setUserId(n.getUserId());
        r.setType(n.getType());
        r.setMessage(n.getMessage());
        r.setSentAt(n.getSentAt());
        r.setReadFlag(n.isReadFlag());
        r.setChannel(n.getChannel());
        r.setReferenceType(n.getReferenceType());
        r.setReferenceId(n.getReferenceId());
        return r;
    }
}
