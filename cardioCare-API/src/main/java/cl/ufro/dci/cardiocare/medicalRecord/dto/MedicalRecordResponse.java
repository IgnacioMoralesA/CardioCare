package cl.ufro.dci.cardiocare.medicalRecord.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class MedicalRecordResponse {
    private Long id;
    private Long patientId;
    private LocalDate recordDate;
    private String description;
    private String recommendations;
    private String createdBy;
    private Instant createdAt;
}
