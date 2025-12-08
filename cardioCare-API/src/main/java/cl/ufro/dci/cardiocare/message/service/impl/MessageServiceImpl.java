package cl.ufro.dci.cardiocare.message.service.impl;

import cl.ufro.dci.cardiocare.message.dto.*;
import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.message.repository.MessageRepository;
import cl.ufro.dci.cardiocare.message.service.MessageService;
import cl.ufro.dci.cardiocare.consultation.repository.ConsultationRepository;
import cl.ufro.dci.cardiocare.security.domain.User;
import cl.ufro.dci.cardiocare.consultation.domain.Consultation;
// Assuming UserRepository exists in security package, based on User location
import cl.ufro.dci.cardiocare.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repo;
    private final UserRepository userRepo;
    private final ConsultationRepository consultationRepo;

    @Override
    public MessageResponse send(MessageRequest req) {
        User sender = userRepo.findById(req.getSenderId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Remitente no encontrado"));

        User receiver = userRepo.findById(req.getReceiverId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Destinatario no encontrado"));

        Message m = new Message();
        m.setSender(sender);
        m.setReceiver(receiver);

        if (req.getConsultationId() != null) {
            Consultation consultation = consultationRepo.findById(req.getConsultationId())
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Consulta no encontrada"));
            m.setConsultation(consultation);
        }

        m.setContent(req.getContent());
        m.setSentAt(Instant.now());
        m.setReadFlag(false);

        repo.save(m);
        return toResponse(m);
    }

    @Override
    public List<MessageResponse> getByConsultation(Long consultationId) {
        return repo.findByConsultation_Id(consultationId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponse> getInbox(Long receiverId) {
        return repo.findByReceiver_Id(receiverId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponse> getSent(Long senderId) {
        return repo.findBySender_Id(senderId).stream()
                .map(this::toResponse)
                .toList();
    }

    private MessageResponse toResponse(Message m) {
        MessageResponse r = new MessageResponse();
        r.setId(m.getId());
        r.setSenderId(m.getSender().getId());
        r.setReceiverId(m.getReceiver().getId());
        r.setConsultationId(m.getConsultation() != null ? m.getConsultation().getId() : null);
        r.setContent(m.getContent());
        r.setSentAt(m.getSentAt());
        r.setReadFlag(m.isReadFlag());
        return r;
    }
}
