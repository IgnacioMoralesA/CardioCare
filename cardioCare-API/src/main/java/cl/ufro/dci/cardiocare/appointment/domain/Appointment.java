package cl.ufro.dci.cardiocare.appointment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "appointment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long medicId;

    private ZonedDateTime dateTime;

    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED

    private String modality; // PRESENTIAL, VIRTUAL

    @Column(length = 2000)
    private String notes;
}
