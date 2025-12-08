package cl.ufro.dci.cardiocare.indicators.repository;

import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    List<Indicator> findByMedicalRecord_PatientId(Long patientId);

    List<Indicator> findByType(String type);

    List<Indicator> findByMedicalRecordId(Long medicalRecordId);

    List<Indicator> findByMedicalRecordAndTimestampBetween(
            MedicalRecord mr,
            Instant start,
            Instant end);
}
