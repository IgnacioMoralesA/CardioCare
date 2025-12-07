package cl.ufro.dci.cardiocare.resource.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResourceRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String category;

    private String content;

    private String url;

    private String author;

    private String tags;
}
