package cl.ufro.dci.cardiocare.resource.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ResourceProgressResponse {
    private Long id;
    private Long patientId;
    private Long resourceId;
    private int progressPercent;
    private boolean completed;
    private Instant updatedAt;
}
