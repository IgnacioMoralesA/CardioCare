package cl.ufro.dci.cardiocare.message.repository;

import cl.ufro.dci.cardiocare.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender_Id(Long senderId);

    List<Message> findByReceiver_Id(Long receiverId);

    List<Message> findByConsultation_Id(Long consultationId);
}
