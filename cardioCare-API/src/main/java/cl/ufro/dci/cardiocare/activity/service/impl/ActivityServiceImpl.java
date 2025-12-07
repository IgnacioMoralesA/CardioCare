package cl.ufro.dci.cardiocare.activity.service.impl;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.activity.domain.ActivityType;
import cl.ufro.dci.cardiocare.activity.dto.*;
import cl.ufro.dci.cardiocare.activity.mapper.ActivityMapper;
import cl.ufro.dci.cardiocare.activity.repository.ActivityRepository;
import cl.ufro.dci.cardiocare.activity.service.ActivityService;
import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final PatientRepository patientRepository;
    private final ActivityMapper mapper;

    @Override
    public ActivityDTO createActivity(CreateActivityDTO dto) {
        Patient p = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        Activity a = Activity.builder()
                .patient(p)
                .activityType(dto.getActivityType())
                .activityName(dto.getActivityName())
                .activityDate(dto.getActivityDate())
                .done(false)
                .build();

        return mapper.toDTO(activityRepository.save(a));
    }

    @Override
    public List<ActivityDTO> getActivitiesByPatient(Long patientId) {
        Patient p = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        return activityRepository.findByPatient(p)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ActivityDTO> getActivitiesForDate(Long patientId, LocalDate date) {
        Patient p = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        return activityRepository.findByPatientAndActivityDate(p, date)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public ActivityDTO markAsDone(Long activityId) {
        Activity a = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        a.setDone(true);
        return mapper.toDTO(activityRepository.save(a));
    }

    private Patient findPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
    }

    @Override
    public List<ActivityDTO> getPendingActivities(Long patientId) {
        Patient p = findPatient(patientId);

        return activityRepository.findByPatient(p)
                .stream()
                .filter(a -> !a.isDone())
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<ActivityDTO> getCompletedActivities(Long patientId) {
        Patient p = findPatient(patientId);

        return activityRepository.findByPatient(p)
                .stream()
                .filter(Activity::isDone)
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<ActivityDTO> getActivitiesByType(Long patientId, ActivityType type) {
        Patient p = findPatient(patientId);

        return activityRepository.findByPatient(p)
                .stream()
                .filter(a -> a.getActivityType() == type)
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ActivityDailySummaryDTO getDailySummary(Long patientId, LocalDate date) {
        Patient p = findPatient(patientId);

        List<Activity> activities = activityRepository.findByPatientAndActivityDate(p, date);

        int total = activities.size();
        int completed = (int) activities.stream().filter(Activity::isDone).count();
        double adherence = total == 0 ? 0 : (double) completed / total;

        return ActivityDailySummaryDTO.builder()
                .date(date)
                .totalActivities(total)
                .completed(completed)
                .adherence(adherence)
                .activities(activities.stream().map(mapper::toDTO).toList())
                .build();
    }

    @Override
    public WeeklySummaryDTO getWeeklySummary(Long patientId) {
        Patient p = findPatient(patientId);

        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(6);

        Map<String, Double> adherenceByDay = new LinkedHashMap<>();

        double totalAdherence = 0;

        for (int i = 0; i < 7; i++) {
            LocalDate date = start.plusDays(i);

            List<Activity> dayActivities = activityRepository.findByPatientAndActivityDate(p, date);

            int total = dayActivities.size();
            int completed = (int) dayActivities.stream().filter(Activity::isDone).count();
            double adherence = total == 0 ? 0 : (double) completed / total;

            adherenceByDay.put(date.getDayOfWeek().toString(), adherence);
            totalAdherence += adherence;
        }

        double weeklyAvg = totalAdherence / 7;

        return WeeklySummaryDTO.builder()
                .weeklyAdherence(weeklyAvg)
                .adherenceByDay(adherenceByDay)
                .build();
    }

    @Override
    public MonthlySummaryDTO getMonthlySummary(Long patientId, int year, int month) {
        Patient p = findPatient(patientId);

        List<Activity> all = activityRepository.findByPatient(p);

        List<Activity> filtered = all.stream()
                .filter(a -> a.getActivityDate().getYear() == year &&
                        a.getActivityDate().getMonthValue() == month)
                .toList();

        int total = filtered.size();
        int completed = (int) filtered.stream().filter(Activity::isDone).count();
        double adherence = total == 0 ? 0 : (double) completed / total;

        return MonthlySummaryDTO.builder()
                .year(year)
                .month(month)
                .totalActivities(total)
                .completedActivities(completed)
                .adherence(adherence)
                .build();
    }

    @Override
    public List<ActivityDTO> getTimeline(Long patientId) {
        Patient p = findPatient(patientId);

        return activityRepository.findByPatient(p)
                .stream()
                .sorted(Comparator.comparing(Activity::getActivityDate))
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ActivityDayExtremesDTO getDayExtremes(Long patientId) {
        Patient p = findPatient(patientId);

        Map<LocalDate, Long> countByDay = activityRepository.findByPatient(p)
                .stream()
                .collect(Collectors.groupingBy(Activity::getActivityDate, Collectors.counting()));

        if (countByDay.isEmpty()) {
            return ActivityDayExtremesDTO.builder().build();
        }

        Map.Entry<LocalDate, Long> most = Collections.max(countByDay.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<LocalDate, Long> least = Collections.min(countByDay.entrySet(), Map.Entry.comparingByValue());

        return ActivityDayExtremesDTO.builder()
                .mostActiveDay(most.getKey())
                .mostActiveCount(most.getValue().intValue())
                .leastActiveDay(least.getKey())
                .leastActiveCount(least.getValue().intValue())
                .build();
    }

    @Override
    public ActivityAdherenceDTO getAdherence(Long patientId) {
        Patient p = findPatient(patientId);

        List<Activity> all = activityRepository.findByPatient(p);

        int total = all.size();
        int completed = (int) all.stream().filter(Activity::isDone).count();
        double adherence = total == 0 ? 0 : (double) completed / total;

        return ActivityAdherenceDTO.builder()
                .totalActivities(total)
                .completedActivities(completed)
                .adherence(adherence)
                .build();
    }

    @Override
    public List<ActivityTypeCountDTO> getRanking(Long patientId) {
        Patient p = findPatient(patientId);

        Map<ActivityType, Long> grouped = activityRepository.findByPatient(p)
                .stream()
                .collect(Collectors.groupingBy(Activity::getActivityType, Collectors.counting()));

        return grouped.entrySet().stream()
                .map(e -> new ActivityTypeCountDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> Long.compare(b.getCount(), a.getCount()))
                .toList();
    }
}
