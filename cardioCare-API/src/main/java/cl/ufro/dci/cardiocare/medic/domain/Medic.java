package cl.ufro.dci.cardiocare.medic.domain;

import cl.ufro.dci.cardiocare.security.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medic")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medic {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String specialty;
    private String licenseNumber;

    @Column(columnDefinition = "text")
    private String scheduleJson; // simple json string for schedules
}
