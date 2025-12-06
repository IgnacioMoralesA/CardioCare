package cl.ufro.dci.cardiocare.resource.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "resource")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;

    @Column(length = 5000)
    private String content;

    private String url;

    private LocalDate publicationDate;

    private String author;

    private String tags; // comma-separated: "salud,cardiología,recuperación"
}
