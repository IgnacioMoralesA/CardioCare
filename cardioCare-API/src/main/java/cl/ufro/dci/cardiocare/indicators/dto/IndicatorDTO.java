package cl.ufro.dci.cardiocare.indicators.dto;

import lombok.*;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class IndicatorDTO {

    private Long id;

    private Long medicalRecordId;

    private Long patientId;

    private String type; // HEART_RATE, BLOOD_PRESSURE, etc.

    private Double value;

    private String unit; // bpm, mmHg, %

    private Instant timestamp;
}
