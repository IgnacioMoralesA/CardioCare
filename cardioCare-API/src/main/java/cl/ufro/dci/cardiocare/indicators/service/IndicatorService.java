package cl.ufro.dci.cardiocare.indicators.service;

import cl.ufro.dci.cardiocare.indicators.dto.*;

import java.util.List;

public interface IndicatorService {
    IndicatorResponse create(IndicatorRequest request);
    List<IndicatorResponse> getByPatient(Long patientId);
}
