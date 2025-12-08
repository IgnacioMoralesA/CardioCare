package cl.ufro.dci.cardiocare.dashboard.controller;

import cl.ufro.dci.cardiocare.dashboard.dto.PatientProgressDTO;
import cl.ufro.dci.cardiocare.dashboard.service.DashboardAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard/analytics")
@RequiredArgsConstructor
public class DashboardAnalyticsController {

    private final DashboardAnalyticsService analyticsService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<PatientProgressDTO> getPatientProgress(@PathVariable Long patientId) {
        return ResponseEntity.ok(analyticsService.getPatientProgress(patientId));
    }
}
