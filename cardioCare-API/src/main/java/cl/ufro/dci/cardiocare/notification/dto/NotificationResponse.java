package cl.ufro.dci.cardiocare.notification.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String type;
    private String message;
    private Instant sentAt;
    private boolean readFlag;
    private String channel;
    private String referenceType;
    private Long referenceId;
}
