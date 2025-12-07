package cl.ufro.dci.cardiocare.consultation.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ConsultationRequest {

    @NotNull
    private Long patientId;

    private Long medicId; // optional

    @NotBlank
    private String message;

    private String priority; // LOW, MEDIUM, HIGH
}
