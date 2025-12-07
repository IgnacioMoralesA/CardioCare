package cl.ufro.dci.cardiocare.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AppointmentRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long medicId;

    @NotNull
    private ZonedDateTime dateTime;

    @NotBlank
    private String modality;

    private String notes;
}
