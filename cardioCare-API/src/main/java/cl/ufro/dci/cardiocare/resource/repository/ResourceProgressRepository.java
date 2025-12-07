package cl.ufro.dci.cardiocare.resource.repository;

import cl.ufro.dci.cardiocare.resource.domain.ResourceProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceProgressRepository extends JpaRepository<ResourceProgress, Long> {
    List<ResourceProgress> findByPatientId(Long patientId);
    Optional<ResourceProgress> findByPatientIdAndResourceId(Long patientId, Long resourceId);
}

