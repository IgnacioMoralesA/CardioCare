package cl.ufro.dci.cardiocare.consultation.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ConsultationReplyRequest {

    @NotBlank
    private String response;
}
