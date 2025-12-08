package cl.ufro.dci.cardiocare.activity.mapper;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.activity.dto.ActivityDTO;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO toDTO(Activity a) {
        return ActivityDTO.builder()
                .id(a.getId())
                .patientId(a.getPatient().getId())
                .activityType(a.getActivityType())
                .activityName(a.getActivityName())
                .done(a.isDone())
                .activityDate(a.getActivityDate())
                .build();
    }
}
