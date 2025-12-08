package cl.ufro.dci.cardiocare.medicalRecord.domain;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "medical_record")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private LocalDate recordDate;

    @Column(length = 5000)
    private String description;

    @Column(length = 2000)
    private String recommendations;

    private String createdBy; // doctor email or ID

    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private List<Activity> activities;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private List<Indicator> indicators;
}

