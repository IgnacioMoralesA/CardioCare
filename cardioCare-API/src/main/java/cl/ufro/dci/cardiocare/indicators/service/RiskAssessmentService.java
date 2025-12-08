package cl.ufro.dci.cardiocare.indicators.service;

import cl.ufro.dci.cardiocare.indicators.domain.Indicator;

public interface RiskAssessmentService {
    void evaluateRisk(Indicator indicator);
}
