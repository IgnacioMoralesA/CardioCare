package cl.ufro.dci.cardiocare.indicators.controller;

import cl.ufro.dci.cardiocare.indicators.dto.*;
import cl.ufro.dci.cardiocare.indicators.service.IndicatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/indicators")
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorService service;

    @PostMapping
    public ResponseEntity<IndicatorResponse> create(@Valid @RequestBody IndicatorRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<IndicatorResponse>> getByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByPatient(id));
    }

    @GetMapping("/medical-record/{id}")
    public ResponseEntity<List<IndicatorResponse>> getByMedicalRecord(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByMedicalRecord(id));
    }
}
