package cl.ufro.dci.cardiocare.patient.repository;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
