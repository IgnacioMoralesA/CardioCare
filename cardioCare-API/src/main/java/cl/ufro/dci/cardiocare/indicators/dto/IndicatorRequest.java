package cl.ufro.dci.cardiocare.indicators.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class IndicatorRequest {

    @NotNull
    private Long patientId;

    @NotBlank
    private String type;

    @NotNull
    private Double value;

    private String unit;
}
