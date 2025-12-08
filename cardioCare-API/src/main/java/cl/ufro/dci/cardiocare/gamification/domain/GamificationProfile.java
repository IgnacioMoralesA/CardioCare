package cl.ufro.dci.cardiocare.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "gamification_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamificationProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long patientId;

    private int currentStreak;
    private int longestStreak;
    private int totalPoints;

    private LocalDate lastActivityDate;
}
