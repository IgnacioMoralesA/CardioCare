package cl.ufro.dci.cardiocare.gamification.repository;

import cl.ufro.dci.cardiocare.gamification.domain.GamificationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GamificationRepository extends JpaRepository<GamificationProfile, Long> {
    Optional<GamificationProfile> findByPatientId(Long patientId);
}
