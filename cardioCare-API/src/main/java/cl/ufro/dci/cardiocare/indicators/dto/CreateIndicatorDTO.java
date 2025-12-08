package cl.ufro.dci.cardiocare.indicators.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndicatorDTO {
    private Long patientId;
    private String type;
    private Double value;
    private String unit;
}

