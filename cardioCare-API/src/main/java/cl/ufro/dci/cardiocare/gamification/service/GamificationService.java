package cl.ufro.dci.cardiocare.gamification.service;

import cl.ufro.dci.cardiocare.gamification.domain.GamificationProfile;
import cl.ufro.dci.cardiocare.gamification.repository.GamificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GamificationService {

    private final GamificationRepository repo;

    @Transactional
    public void registerActivityCompletion(Long patientId) {
        GamificationProfile profile = repo.findByPatientId(patientId)
                .orElseGet(() -> createProfile(patientId));

        LocalDate today = LocalDate.now();
        LocalDate lastDate = profile.getLastActivityDate();

        if (lastDate == null) {
            // First ever activity
            profile.setCurrentStreak(1);
            profile.setTotalPoints(profile.getTotalPoints() + 10);
        } else if (lastDate.equals(today)) {
            // Already did activity today, just add points
            profile.setTotalPoints(profile.getTotalPoints() + 5);
        } else if (lastDate.equals(today.minusDays(1))) {
            // Consecutive day
            profile.setCurrentStreak(profile.getCurrentStreak() + 1);
            profile.setTotalPoints(profile.getTotalPoints() + 10 + (profile.getCurrentStreak() * 2)); // Bonus for
                                                                                                      // streak
        } else {
            // Streak broken
            profile.setCurrentStreak(1);
            profile.setTotalPoints(profile.getTotalPoints() + 10);
        }

        if (profile.getCurrentStreak() > profile.getLongestStreak()) {
            profile.setLongestStreak(profile.getCurrentStreak());
        }

        profile.setLastActivityDate(today);
        repo.save(profile);
    }

    public GamificationProfile getProfile(Long patientId) {
        return repo.findByPatientId(patientId)
                .orElseGet(() -> createProfile(patientId));
    }

    private GamificationProfile createProfile(Long patientId) {
        return GamificationProfile.builder()
                .patientId(patientId)
                .currentStreak(0)
                .longestStreak(0)
                .totalPoints(0)
                .build();
    }
}
