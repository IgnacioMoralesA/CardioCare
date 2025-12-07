package cl.ufro.dci.cardiocare.indicators.service.impl;

import cl.ufro.dci.cardiocare.indicators.dto.*;
import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.indicators.repository.IndicatorRepository;
import cl.ufro.dci.cardiocare.indicators.service.IndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {

    private final IndicatorRepository repo;

    @Override
    public IndicatorResponse create(IndicatorRequest req) {
        Indicator i = new Indicator();
        i.setPatientId(req.getPatientId());
        i.setType(req.getType());
        i.setValue(req.getValue());
        i.setUnit(req.getUnit());
        i.setTimestamp(Instant.now());

        repo.save(i);
        return toResponse(i);
    }

    @Override
    public List<IndicatorResponse> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId).stream()
                .map(this::toResponse)
                .toList();
    }

    private IndicatorResponse toResponse(Indicator i) {
        IndicatorResponse r = new IndicatorResponse();
        r.setId(i.getId());
        r.setPatientId(i.getPatientId());
        r.setType(i.getType());
        r.setValue(i.getValue());
        r.setTimestamp(i.getTimestamp());
        r.setUnit(i.getUnit());
        return r;
    }
}
