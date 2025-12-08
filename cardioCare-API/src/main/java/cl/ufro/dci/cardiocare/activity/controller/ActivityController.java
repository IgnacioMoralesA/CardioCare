package cl.ufro.dci.cardiocare.activity.controller;

import cl.ufro.dci.cardiocare.activity.dto.ActivityDTO;
import cl.ufro.dci.cardiocare.activity.dto.CreateActivityDTO;
import cl.ufro.dci.cardiocare.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityDTO create(@RequestBody CreateActivityDTO dto) {
        return activityService.createActivity(dto);
    }

    @GetMapping("/patient/{patientId}")
    public List<ActivityDTO> findAllByPatient(@PathVariable Long patientId) {
        return activityService.getActivitiesByPatient(patientId);
    }

    @GetMapping("/patient/{patientId}/date")
    public List<ActivityDTO> findActivitiesForDate(
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return activityService.getActivitiesForDate(patientId, date);
    }

    @PatchMapping("/{id}/done")
    public ActivityDTO markAsDone(@PathVariable Long id) {
        return activityService.markAsDone(id);
    }
}
