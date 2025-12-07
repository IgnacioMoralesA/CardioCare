package cl.ufro.dci.cardiocare.dashboard.controller;

import cl.ufro.dci.cardiocare.dashboard.dto.*;
import cl.ufro.dci.cardiocare.dashboard.service.DashboardItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardItemController {

    private final DashboardItemService service;

    @PostMapping
    public ResponseEntity<DashboardItemResponse> create(@Valid @RequestBody DashboardItemRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<DashboardItemResponse>> getByOwner(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByOwner(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
