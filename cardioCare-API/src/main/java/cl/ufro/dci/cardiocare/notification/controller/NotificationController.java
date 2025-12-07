package cl.ufro.dci.cardiocare.notification.controller;

import cl.ufro.dci.cardiocare.notification.dto.*;
import cl.ufro.dci.cardiocare.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(service.send(request));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<NotificationResponse>> getByUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByUser(id));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}
