package cl.ufro.dci.cardiocare.notification.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String type;

    @NotBlank
    private String message;

    private String channel;

    private String referenceType;
    private Long referenceId;
}
