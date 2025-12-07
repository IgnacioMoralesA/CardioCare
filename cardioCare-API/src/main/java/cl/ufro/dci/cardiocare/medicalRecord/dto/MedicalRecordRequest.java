package cl.ufro.dci.cardiocare.medicalRecord.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private LocalDate recordDate;

    @NotBlank
    private String description;

    private String recommendations;

    @NotBlank
    private String createdBy;
}
