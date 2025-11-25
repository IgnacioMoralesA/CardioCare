package cl.ufro.dci.cardiocare.message.repository;

import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByPatientId(Long patientId);
}
