package cl.ufro.dci.cardiocare.gamification.controller;

import cl.ufro.dci.cardiocare.gamification.domain.GamificationProfile;
import cl.ufro.dci.cardiocare.gamification.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gamification")
@RequiredArgsConstructor
public class GamificationController {

    private final GamificationService service;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<GamificationProfile> getProfile(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getProfile(patientId));
    }
}
