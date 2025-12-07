package cl.ufro.dci.cardiocare.medic.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MedicRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String specialty;

    @NotBlank
    private String licenseNumber;

    private String scheduleJson;
}
