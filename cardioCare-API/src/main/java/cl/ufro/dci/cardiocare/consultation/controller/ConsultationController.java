package cl.ufro.dci.cardiocare.consultation.controller;

import cl.ufro.dci.cardiocare.consultation.dto.*;
import cl.ufro.dci.cardiocare.consultation.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT','FAMILY')")
    public ResponseEntity<ConsultationResponse> create(@RequestBody @Valid ConsultationRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PostMapping("/{id}/reply")
    @PreAuthorize("hasRole('MEDIC')")
    public ResponseEntity<ConsultationResponse> reply(
            @PathVariable Long id,
            @RequestBody @Valid ConsultationReplyRequest req) {
        return ResponseEntity.ok(service.reply(id, req));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC','PATIENT')")
    public ResponseEntity<ConsultationResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/patient/{pId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC','PATIENT')")
    public ResponseEntity<List<ConsultationResponse>> getByPatient(@PathVariable Long pId) {
        return ResponseEntity.ok(service.getByPatient(pId));
    }

    @GetMapping("/medic/{mId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC')")
    public ResponseEntity<List<ConsultationResponse>> getByMedic(@PathVariable Long mId) {
        return ResponseEntity.ok(service.getByMedic(mId));
    }
}
