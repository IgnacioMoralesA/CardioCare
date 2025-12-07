package cl.ufro.dci.cardiocare.appointment.controller;

import cl.ufro.dci.cardiocare.appointment.dto.*;
import cl.ufro.dci.cardiocare.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC','PATIENT')")
    public ResponseEntity<AppointmentResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC')")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentUpdateStatusRequest req) {
        return ResponseEntity.ok(service.updateStatus(id, req));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC','PATIENT')")
    public ResponseEntity<List<AppointmentResponse>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }

    @GetMapping("/medic/{medicId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDIC')")
    public ResponseEntity<List<AppointmentResponse>> getByMedic(@PathVariable Long medicId) {
        return ResponseEntity.ok(service.getByMedic(medicId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
