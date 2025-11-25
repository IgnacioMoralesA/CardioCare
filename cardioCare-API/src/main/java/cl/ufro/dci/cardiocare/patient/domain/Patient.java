package cl.ufro.dci.cardiocare.patient.domain;

import cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;
    private String nombre;
    private String apellido;
    private int edad;
    private String prioridad; // Alta, Media, Baja

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MedicalRecord> historial;

}

