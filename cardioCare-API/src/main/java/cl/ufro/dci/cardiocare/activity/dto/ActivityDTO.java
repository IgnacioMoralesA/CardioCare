package cl.ufro.dci.cardiocare.activity.dto;

import cl.ufro.dci.cardiocare.activity.domain.ActivityType;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityDTO {
    private Long id;
    private Long patientId;
    private ActivityType activityType;
    private String activityName;
    private boolean done;
    private LocalDate activityDate;
}
