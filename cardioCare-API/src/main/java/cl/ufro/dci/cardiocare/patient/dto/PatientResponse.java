package cl.ufro.dci.cardiocare.patient.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponse {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private LocalDate surgeryDate;
    private String medicalCondition;
}
