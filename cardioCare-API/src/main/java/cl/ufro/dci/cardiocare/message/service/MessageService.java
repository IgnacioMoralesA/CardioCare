package cl.ufro.dci.cardiocare.message.service;

import cl.ufro.dci.cardiocare.message.domain.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByPatient(Long patientId);
    Message sendMessage(Message message);
    long countUnread();
}
