package cl.ufro.dci.cardiocare.message.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "message")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;      // userId
    private Long receiverId;    // userId
    private Long consultationId;

    @Column(length = 2000)
    private String content;

    private Instant sentAt = Instant.now();

    private boolean readFlag = false;
}

