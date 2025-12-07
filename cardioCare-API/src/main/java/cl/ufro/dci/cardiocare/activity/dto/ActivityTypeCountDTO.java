package cl.ufro.dci.cardiocare.activity.dto;

import cl.ufro.dci.cardiocare.activity.domain.ActivityType;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityTypeCountDTO {
    private ActivityType type;
    private long count;
}
