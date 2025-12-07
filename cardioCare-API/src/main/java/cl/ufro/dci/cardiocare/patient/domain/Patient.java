package cl.ufro.dci.cardiocare.patient.domain;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.security.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();
}
