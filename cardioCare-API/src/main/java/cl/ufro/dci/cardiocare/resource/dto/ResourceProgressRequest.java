package cl.ufro.dci.cardiocare.resource.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResourceProgressRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long resourceId;

    private int progressPercent;

    private boolean completed;
}
