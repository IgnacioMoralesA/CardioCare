package cl.ufro.dci.cardiocare.medicalRecord.repository;

import cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
}

