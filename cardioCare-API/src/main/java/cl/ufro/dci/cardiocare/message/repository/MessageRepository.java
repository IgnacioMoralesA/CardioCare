package cl.ufro.dci.cardiocare.message.repository;

import cl.ufro.dci.cardiocare.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findByConsultationId(Long consultationId);
}

