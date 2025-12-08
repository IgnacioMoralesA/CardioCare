package cl.ufro.dci.cardiocare.indicators.domain;


import cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "indicator")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    private String type; // e.g. HEART_RATE, BLOOD_PRESSURE, SPO2

    private Double value;

    private Instant timestamp = Instant.now();

    private String unit; // "bpm", "mmHg", "%"
}

