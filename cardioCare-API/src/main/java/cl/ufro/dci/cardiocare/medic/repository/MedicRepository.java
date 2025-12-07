package cl.ufro.dci.cardiocare.medic.repository;

import cl.ufro.dci.cardiocare.medic.domain.Medic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicRepository extends JpaRepository<Medic, Long> {
}
