package cl.ufro.dci.cardiocare.patient.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {

    @NotNull
    private Long userId;

    private String gender;

    private LocalDate birthDate;

    private LocalDate surgeryDate;

    private String medicalCondition;
}
