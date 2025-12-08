package cl.ufro.dci.cardiocare.activity.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityDailySummaryDTO {

    private LocalDate date;
    private int totalActivities;
    private int completed;
    private double adherence; // completed / total
    private List<ActivityDTO> activities;
}
