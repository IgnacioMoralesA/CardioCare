package cl.ufro.dci.cardiocare.patient.service;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import org.springframework.data.domain.Page;      // IMPORTANTE
import org.springframework.data.domain.Pageable;  // IMPORTANTE

public interface PatientService {
    // Cambiamos List por Page y añadimos el parámetro Pageable
    Page<Patient> getAll(Pageable pageable);

    Patient getById(Long id);
    Patient save(Patient patient);
}