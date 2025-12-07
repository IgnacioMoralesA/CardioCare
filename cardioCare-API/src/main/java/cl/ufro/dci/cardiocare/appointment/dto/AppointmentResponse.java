package cl.ufro.dci.cardiocare.appointment.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private Long medicId;
    private ZonedDateTime dateTime;
    private String status;
    private String modality;
    private String notes;
}
