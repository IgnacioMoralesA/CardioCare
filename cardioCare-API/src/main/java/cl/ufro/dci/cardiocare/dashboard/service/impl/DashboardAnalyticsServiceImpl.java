package cl.ufro.dci.cardiocare.dashboard.service.impl;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.activity.repository.ActivityRepository;
import cl.ufro.dci.cardiocare.appointment.repository.AppointmentRepository;
import cl.ufro.dci.cardiocare.dashboard.dto.GraphDataPoint;
import cl.ufro.dci.cardiocare.dashboard.dto.IndicatorTrendDTO;
import cl.ufro.dci.cardiocare.dashboard.dto.PatientProgressDTO;
import cl.ufro.dci.cardiocare.dashboard.service.DashboardAnalyticsService;
import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.indicators.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardAnalyticsServiceImpl implements DashboardAnalyticsService {

        private final ActivityRepository activityRepository;
        private final IndicatorRepository indicatorRepository;
        private final AppointmentRepository appointmentRepository;
        private final cl.ufro.dci.cardiocare.patient.repository.PatientRepository patientRepository;

        @Override
        public PatientProgressDTO getPatientProgress(Long patientId) {
                // 0. Verify Patient Existence and Get Entity
                cl.ufro.dci.cardiocare.patient.domain.Patient patient = patientRepository.findById(patientId)
                                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                                                "Paciente no encontrado"));

                // 1. Calculate Activity Adherence
                List<Activity> allActivities = activityRepository.findByPatient(patient);
                long total = allActivities.size();
                long completed = allActivities.stream().filter(Activity::isDone).count();
                double adherence = total > 0 ? (double) completed / total * 100 : 0.0;

                // 2. Get Next Appointment
                LocalDateTime nextAppt = appointmentRepository.findByPatientId(patientId).stream()
                                .filter(a -> a.getDateTime().toLocalDateTime().isAfter(LocalDateTime.now()))
                                .map(a -> a.getDateTime().toLocalDateTime())
                                .min(LocalDateTime::compareTo)
                                .orElse(null);

                // 3. Get Vital Signs Trends
                List<Indicator> indicators = indicatorRepository.findByMedicalRecord_PatientId(patientId);

                // Group by Type
                Map<String, List<Indicator>> byType = indicators.stream()
                                .collect(Collectors.groupingBy(Indicator::getType));

                List<IndicatorTrendDTO> trends = byType.entrySet().stream()
                                .map(entry -> {
                                        String type = entry.getKey();
                                        List<Indicator> typeList = entry.getValue();
                                        String unit = typeList.isEmpty() ? "" : typeList.get(0).getUnit();

                                        List<GraphDataPoint> points = typeList.stream()
                                                        .map(i -> new GraphDataPoint(
                                                                        i.getTimestamp().atZone(java.time.ZoneId
                                                                                        .systemDefault()).toLocalDate(),
                                                                        i.getValue()))
                                                        .sorted((p1, p2) -> p1.getDate().compareTo(p2.getDate()))
                                                        .collect(Collectors.toList());

                                        return new IndicatorTrendDTO(type, unit, points);
                                })
                                .collect(Collectors.toList());

                return PatientProgressDTO.builder()
                                .patientId(patientId)
                                .totalActivities(total)
                                .completedActivities(completed)
                                .adherencePercentage(adherence)
                                .nextAppointment(nextAppt)
                                .vitalSigns(trends)
                                .build();
        }
}
