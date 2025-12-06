package cl.ufro.dci.cardiocare.resource.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "resource_progress")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private Long resourceId;

    private boolean completed = false;

    private int progressPercent = 0;

    private Instant updatedAt = Instant.now();
}

