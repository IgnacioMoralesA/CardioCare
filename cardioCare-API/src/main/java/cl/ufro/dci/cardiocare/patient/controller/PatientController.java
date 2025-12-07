package cl.ufro.dci.cardiocare.patient.controller;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;      // IMPORTANTE
import org.springframework.data.domain.Pageable;  // IMPORTANTE
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    // Spring inyecta automáticamente 'page', 'size', 'sort' desde la URL
    @GetMapping
    public Page<Patient> listar(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Patient obtener(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Patient crear(@RequestBody Patient p) {
        return service.save(p);
    }
}