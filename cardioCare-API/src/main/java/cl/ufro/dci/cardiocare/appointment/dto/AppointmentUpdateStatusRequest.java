package cl.ufro.dci.cardiocare.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AppointmentUpdateStatusRequest {

    @NotBlank
    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED
}