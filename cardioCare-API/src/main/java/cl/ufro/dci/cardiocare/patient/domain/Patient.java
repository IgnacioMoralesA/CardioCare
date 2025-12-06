package cl.ufro.dci.cardiocare.patient.domain;

import cl.ufro.dci.cardiocare.security.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id
    private Long id; // same id as user

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private LocalDate birthDate;
    private String gender;
    @Column(length = 2000)
    private String medicalCondition;
    private LocalDate surgeryDate;
}
