package cl.ufro.dci.cardiocare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProgressDTO {
    private Long patientId;

    // Activity Stats
    private long totalActivities;
    private long completedActivities;
    private double adherencePercentage;

    // Next Appointment
    private LocalDateTime nextAppointment;

    // Vitals
    private List<IndicatorTrendDTO> vitalSigns;
}
