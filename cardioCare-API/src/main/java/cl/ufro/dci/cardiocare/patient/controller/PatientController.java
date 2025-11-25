package cl.ufro.dci.cardiocare.patient.controller;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @GetMapping
    public List<Patient> listar() {
        return service.getAll();
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
