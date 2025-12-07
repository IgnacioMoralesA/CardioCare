package cl.ufro.dci.cardiocare.activity.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityAdherenceDTO {

    private int totalActivities;
    private int completedActivities;
    private double adherence;
}
