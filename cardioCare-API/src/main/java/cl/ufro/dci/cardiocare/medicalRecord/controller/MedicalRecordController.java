package cl.ufro.dci.cardiocare.medicalRecord.controller;

import cl.ufro.dci.cardiocare.medicalRecord.dto.*;
import cl.ufro.dci.cardiocare.medicalRecord.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping
    @PreAuthorize("hasRole('MEDIC')")
    public ResponseEntity<MedicalRecordResponse> create(@Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('MEDIC','ADMIN')")
    public ResponseEntity<List<MedicalRecordResponse>> getByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByPatient(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
