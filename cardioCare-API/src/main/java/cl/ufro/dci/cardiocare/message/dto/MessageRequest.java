package cl.ufro.dci.cardiocare.message.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MessageRequest {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private Long consultationId;

    @NotBlank
    private String content;
}
