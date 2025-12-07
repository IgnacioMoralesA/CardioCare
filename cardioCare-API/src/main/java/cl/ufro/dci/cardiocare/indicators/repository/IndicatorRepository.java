package cl.ufro.dci.cardiocare.indicators.repository;

import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    List<Indicator> findByPatientId(Long patientId);
    List<Indicator> findByType(String type);
}

