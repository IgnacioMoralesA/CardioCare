package cl.ufro.dci.cardiocare.activity.service;

import cl.ufro.dci.cardiocare.activity.domain.ActivityType;
import cl.ufro.dci.cardiocare.activity.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface ActivityService {

    ActivityDTO createActivity(CreateActivityDTO dto);

    List<ActivityDTO> getActivitiesByPatient(Long patientId);

    List<ActivityDTO> getActivitiesForDate(Long patientId, LocalDate date);

    ActivityDTO markAsDone(Long activityId);

    List<ActivityDTO> getPendingActivities(Long patientId);

    List<ActivityDTO> getCompletedActivities(Long patientId);

    List<ActivityDTO> getActivitiesByType(Long patientId, ActivityType type);

    ActivityDailySummaryDTO getDailySummary(Long patientId, LocalDate date);

    WeeklySummaryDTO getWeeklySummary(Long patientId);

    MonthlySummaryDTO getMonthlySummary(Long patientId, int year, int month);

    List<ActivityDTO> getTimeline(Long patientId);

    ActivityDayExtremesDTO getDayExtremes(Long patientId);

    ActivityAdherenceDTO getAdherence(Long patientId);

    List<ActivityTypeCountDTO> getRanking(Long patientId);
}
