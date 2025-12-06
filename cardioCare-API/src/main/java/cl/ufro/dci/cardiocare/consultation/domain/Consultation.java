package cl.ufro.dci.cardiocare.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "consultation")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long medicId; // nullable until assigned

    @Column(length = 4000)
    private String message;

    private Instant sentAt = Instant.now();

    private String priority; // LOW, MEDIUM, HIGH
    private String status; // OPEN, RESPONDED, CLOSED

    @Column(length = 4000)
    private String medicResponse;
    private Instant respondedAt;
}
