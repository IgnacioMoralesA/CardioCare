package cl.ufro.dci.cardiocare.indicators.service.impl;

import cl.ufro.dci.cardiocare.indicators.dto.*;
import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.indicators.mapper.IndicatorMapper;
import cl.ufro.dci.cardiocare.indicators.repository.IndicatorRepository;
import cl.ufro.dci.cardiocare.indicators.service.IndicatorService;
import cl.ufro.dci.cardiocare.medicalRecord.repository.MedicalRecordRepository;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class IndicatorServiceImpl implements IndicatorService {

    private final IndicatorRepository repo;
    private final MedicalRecordRepository mrRepo;
    private final IndicatorMapper mapper;

    @Override
    public IndicatorResponse create(IndicatorRequest req) {
        cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord mr = mrRepo.findById(req.getMedicalRecordId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Ficha medica no encontrada"));

        Indicator i = new Indicator();
        i.setMedicalRecord(mr);
        i.setType(req.getType());
        i.setValue(req.getValue());
        i.setUnit(req.getUnit());
        i.setTimestamp(Instant.now());

        repo.save(i);
        return toResponse(i);
    }

    @Override
    public List<IndicatorResponse> getByPatient(Long patientId) {
        return repo.findByMedicalRecord_PatientId(patientId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<IndicatorResponse> getByMedicalRecord(Long medicalRecordId) {
        return repo.findByMedicalRecordId(medicalRecordId).stream()
                .map(this::toResponse)
                .toList();
    }

    private IndicatorResponse toResponse(Indicator i) {
        IndicatorResponse r = new IndicatorResponse();
        r.setId(i.getId());
        r.setPatientId(i.getMedicalRecord().getPatient().getId()); // Derive patientId from MedicalRecord
        r.setType(i.getType());
        r.setValue(i.getValue());
        r.setTimestamp(i.getTimestamp());
        r.setUnit(i.getUnit());
        return r;
    }
}
