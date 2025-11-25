package cl.ufro.dci.cardiocare.medicalRecord.domain;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String diagnostico;
    private String notas;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}

