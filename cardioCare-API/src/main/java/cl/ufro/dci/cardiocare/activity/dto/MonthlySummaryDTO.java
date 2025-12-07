package cl.ufro.dci.cardiocare.activity.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MonthlySummaryDTO {

    private int month;
    private int year;
    private int totalActivities;
    private int completedActivities;
    private double adherence;
}
