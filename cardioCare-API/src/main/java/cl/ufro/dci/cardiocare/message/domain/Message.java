package cl.ufro.dci.cardiocare.message.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String asunto;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private boolean leido;
    private String prioridad;
    private LocalDateTime fecha;

    @ElementCollection
    private List<String> tags;
}
