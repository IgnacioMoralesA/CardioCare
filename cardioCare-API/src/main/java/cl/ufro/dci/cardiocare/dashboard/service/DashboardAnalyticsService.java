package cl.ufro.dci.cardiocare.dashboard.service;

import cl.ufro.dci.cardiocare.dashboard.dto.PatientProgressDTO;

public interface DashboardAnalyticsService {
    PatientProgressDTO getPatientProgress(Long patientId);
}
