package cl.ufro.dci.cardiocare.message.controller;

import cl.ufro.dci.cardiocare.message.dto.*;
import cl.ufro.dci.cardiocare.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDIC','PATIENT','FAMILY')")
    public ResponseEntity<MessageResponse> send(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(service.send(request));
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<List<MessageResponse>> getByConsultation(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByConsultation(id));
    }

    @GetMapping("/inbox/{userId}")
    public ResponseEntity<List<MessageResponse>> inbox(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getInbox(userId));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<MessageResponse>> sent(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getSent(userId));
    }
}
