package cl.ufro.dci.cardiocare.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private cl.ufro.dci.cardiocare.patient.domain.Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medic_id")
    private cl.ufro.dci.cardiocare.medic.domain.Medic medic; // nullable until assigned

    @Column(length = 4000)
    private String message;

    private Instant sentAt = Instant.now();

    private String priority; // LOW, MEDIUM, HIGH
    private String status; // OPEN, RESPONDED, CLOSED

    @Column(length = 4000)
    private String medicResponse;
    private Instant respondedAt;
}
