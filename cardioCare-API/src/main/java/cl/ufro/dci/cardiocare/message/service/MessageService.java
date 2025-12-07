package cl.ufro.dci.cardiocare.message.service;

import cl.ufro.dci.cardiocare.message.dto.*;

import java.util.List;

public interface MessageService {
    MessageResponse send(MessageRequest request);
    List<MessageResponse> getByConsultation(Long consultationId);
    List<MessageResponse> getInbox(Long receiverId);
    List<MessageResponse> getSent(Long senderId);
}
