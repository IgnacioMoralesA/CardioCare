package cl.ufro.dci.cardiocare.indicators.service;

import cl.ufro.dci.cardiocare.indicators.dto.*;

import java.time.Instant;
import java.util.List;

public interface IndicatorService {
    IndicatorResponse create(IndicatorRequest request);

    List<IndicatorResponse> getByPatient(Long patientId);

    List<IndicatorResponse> getByMedicalRecord(Long medicalRecordId);
}
