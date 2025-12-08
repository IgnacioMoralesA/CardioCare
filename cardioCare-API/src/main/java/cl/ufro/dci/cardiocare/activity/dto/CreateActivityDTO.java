package cl.ufro.dci.cardiocare.activity.dto;

import cl.ufro.dci.cardiocare.activity.domain.ActivityType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateActivityDTO {

    @NotNull
    private Long patientId;

    @NotNull
    private ActivityType activityType;

    @NotBlank
    @Size(max = 200)
    private String activityName;

    @NotNull
    private LocalDate activityDate;
}
