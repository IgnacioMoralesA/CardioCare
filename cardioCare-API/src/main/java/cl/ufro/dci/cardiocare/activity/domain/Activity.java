package cl.ufro.dci.cardiocare.activity.domain;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient_daily_activity")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    // Tipo predefinido de actividad
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type_id", nullable = false)
    private ActivityType activityType;

    @Column(name = "activity_name", nullable = false, length = 200)
    private String activityName;

    @Column(nullable = false)
    private boolean done = false;

    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;
}
