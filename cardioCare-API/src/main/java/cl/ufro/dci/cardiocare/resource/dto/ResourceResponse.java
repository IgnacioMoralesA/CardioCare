package cl.ufro.dci.cardiocare.resource.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResourceResponse {
    private Long id;
    private String title;
    private String category;
    private String content;
    private String url;
    private LocalDate publicationDate;
    private String author;
    private String tags;
}
