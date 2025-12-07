package cl.ufro.dci.cardiocare.activity.dto;

import lombok.*;

import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WeeklySummaryDTO {

    private double weeklyAdherence;
    private Map<String, Double> adherenceByDay; // LUN = 0.8, MAR = 1.0...
}
