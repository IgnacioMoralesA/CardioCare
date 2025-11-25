package cl.ufro.dci.cardiocare.medic.controller;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final PatientRepository patientRepo;

    @GetMapping("/pacientes")
    public List<Patient> exportPacientes() {
        return patientRepo.findAll();
    }
}

