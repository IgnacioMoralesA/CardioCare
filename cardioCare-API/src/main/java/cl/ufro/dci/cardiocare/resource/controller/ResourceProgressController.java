package cl.ufro.dci.cardiocare.resource.controller;

import cl.ufro.dci.cardiocare.resource.dto.*;
import cl.ufro.dci.cardiocare.resource.service.ResourceProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource-progress")
@RequiredArgsConstructor
public class ResourceProgressController {

    private final ResourceProgressService service;

    @PostMapping
    public ResponseEntity<ResourceProgressResponse> update(@Valid @RequestBody ResourceProgressRequest request) {
        return ResponseEntity.ok(service.updateProgress(request));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<ResourceProgressResponse>> getByPatient(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByPatient(id));
    }
}
