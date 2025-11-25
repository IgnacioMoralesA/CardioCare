package cl.ufro.dci.cardiocare.message.service;

import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repo;

    @Override
    public List<Message> getMessagesByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    @Override
    public Message sendMessage(Message msg) {
        msg.setFecha(LocalDateTime.now());
        msg.setLeido(false);
        return repo.save(msg);
    }

    @Override
    public long countUnread() {
        return repo.findAll().stream().filter(m -> !m.isLeido()).count();
    }
}
