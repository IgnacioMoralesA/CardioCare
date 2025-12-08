package cl.ufro.dci.cardiocare.medicalRecord.service.impl;

import cl.ufro.dci.cardiocare.medicalRecord.dto.*;
import cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord;
import cl.ufro.dci.cardiocare.medicalRecord.repository.MedicalRecordRepository;
import cl.ufro.dci.cardiocare.medicalRecord.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repo;
    private final cl.ufro.dci.cardiocare.patient.repository.PatientRepository patientRepo;

    @Override
    public MedicalRecordResponse create(MedicalRecordRequest req) {
        if (!patientRepo.existsById(req.getPatientId())) {
            throw new jakarta.persistence.EntityNotFoundException("Paciente no encontrado");
        }

        MedicalRecord mr = new MedicalRecord();
        mr.setPatientId(req.getPatientId());
        mr.setRecordDate(req.getRecordDate());
        mr.setDescription(req.getDescription());
        mr.setRecommendations(req.getRecommendations());
        mr.setCreatedBy(req.getCreatedBy());
        mr.setCreatedAt(Instant.now());

        repo.save(mr);
        return toResponse(mr);
    }

    @Override
    public List<MedicalRecordResponse> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private MedicalRecordResponse toResponse(MedicalRecord mr) {
        MedicalRecordResponse r = new MedicalRecordResponse();
        r.setId(mr.getId());
        r.setPatientId(mr.getPatientId());
        r.setRecordDate(mr.getRecordDate());
        r.setDescription(mr.getDescription());
        r.setRecommendations(mr.getRecommendations());
        r.setCreatedBy(mr.getCreatedBy());
        r.setCreatedAt(mr.getCreatedAt());

        if (mr.getIndicators() != null) {
            r.setIndicators(mr.getIndicators().stream()
                    .map(i -> {
                        cl.ufro.dci.cardiocare.indicators.dto.IndicatorResponse ir = new cl.ufro.dci.cardiocare.indicators.dto.IndicatorResponse();
                        ir.setId(i.getId());
                        ir.setPatientId(mr.getPatientId());
                        ir.setType(i.getType());
                        ir.setValue(i.getValue());
                        ir.setTimestamp(i.getTimestamp());
                        ir.setUnit(i.getUnit());
                        return ir;
                    }).toList());
        }

        return r;
    }
}
