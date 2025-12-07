package cl.ufro.dci.cardiocare.activity.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityDayExtremesDTO {

    private LocalDate mostActiveDay;
    private int mostActiveCount;

    private LocalDate leastActiveDay;
    private int leastActiveCount;
}
