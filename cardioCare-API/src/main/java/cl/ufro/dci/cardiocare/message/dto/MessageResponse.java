package cl.ufro.dci.cardiocare.message.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class MessageResponse {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long consultationId;
    private String content;
    private Instant sentAt;
    private boolean readFlag;
}
