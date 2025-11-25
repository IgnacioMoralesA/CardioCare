package cl.ufro.dci.cardiocare.patient.service;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import java.util.List;

public interface PatientService {
    List<Patient> getAll();
    Patient getById(Long id);
    Patient save(Patient patient);
}
