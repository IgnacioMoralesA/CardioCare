package cl.ufro.dci.cardiocare.message.service.impl;

import cl.ufro.dci.cardiocare.message.dto.*;
import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.message.repository.MessageRepository;
import cl.ufro.dci.cardiocare.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repo;

    @Override
    public MessageResponse send(MessageRequest req) {
        Message m = new Message();
        m.setSenderId(req.getSenderId());
        m.setReceiverId(req.getReceiverId());
        m.setConsultationId(req.getConsultationId());
        m.setContent(req.getContent());
        m.setSentAt(Instant.now());
        m.setReadFlag(false);

        repo.save(m);
        return toResponse(m);
    }

    @Override
    public List<MessageResponse> getByConsultation(Long consultationId) {
        return repo.findByConsultationId(consultationId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponse> getInbox(Long receiverId) {
        return repo.findByReceiverId(receiverId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponse> getSent(Long senderId) {
        return repo.findBySenderId(senderId).stream()
                .map(this::toResponse)
                .toList();
    }

    private MessageResponse toResponse(Message m) {
        MessageResponse r = new MessageResponse();
        r.setId(m.getId());
        r.setSenderId(m.getSenderId());
        r.setReceiverId(m.getReceiverId());
        r.setConsultationId(m.getConsultationId());
        r.setContent(m.getContent());
        r.setSentAt(m.getSentAt());
        r.setReadFlag(m.isReadFlag());
        return r;
    }
}
