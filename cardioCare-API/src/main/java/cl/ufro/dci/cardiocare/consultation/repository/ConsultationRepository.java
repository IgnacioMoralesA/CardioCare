package cl.ufro.dci.cardiocare.consultation.repository;

import cl.ufro.dci.cardiocare.consultation.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientId(Long patientId);
    List<Consultation> findByMedicId(Long medicId);
}

