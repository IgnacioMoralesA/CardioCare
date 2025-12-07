package cl.ufro.dci.cardiocare.consultation.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ConsultationResponse {

    private Long id;
    private Long patientId;
    private Long medicId;
    private String message;
    private String priority;
    private String status;

    private String medicResponse;
    private Instant sentAt;
    private Instant respondedAt;
}
