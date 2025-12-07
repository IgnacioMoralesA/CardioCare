package cl.ufro.dci.cardiocare.indicators.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class IndicatorResponse {
    private Long id;
    private Long patientId;
    private String type;
    private Double value;
    private Instant timestamp;
    private String unit;
}
