package cl.ufro.dci.cardiocare.notification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "notification")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type;
    @Column(length = 1000)
    private String message;
    private Instant sentAt = Instant.now();
    private boolean readFlag = false;
    private String channel; // PUSH, EMAIL, IN_APP
    private String referenceType; // APPOINTMENT, CONSULTATION, RESOURCE
    private Long referenceId;
}
