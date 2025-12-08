package cl.ufro.dci.cardiocare.message.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private cl.ufro.dci.cardiocare.security.domain.User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private cl.ufro.dci.cardiocare.security.domain.User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private cl.ufro.dci.cardiocare.consultation.domain.Consultation consultation;

    @Column(length = 2000)
    private String content;

    private Instant sentAt = Instant.now();

    private boolean readFlag = false;
}
