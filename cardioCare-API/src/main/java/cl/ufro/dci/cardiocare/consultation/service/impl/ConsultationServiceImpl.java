package cl.ufro.dci.cardiocare.consultation.service.impl;

import cl.ufro.dci.cardiocare.consultation.dto.*;
import cl.ufro.dci.cardiocare.consultation.domain.Consultation;
import cl.ufro.dci.cardiocare.consultation.repository.ConsultationRepository;
import cl.ufro.dci.cardiocare.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository repo;

    @Override
    public ConsultationResponse create(ConsultationRequest req) {
        Consultation c = new Consultation();
        c.setPatientId(req.getPatientId());
        c.setMedicId(req.getMedicId());
        c.setMessage(req.getMessage());
        c.setPriority(req.getPriority());
        c.setStatus("OPEN");
        c.setSentAt(Instant.now());

        repo.save(c);
        return toResponse(c);
    }

    @Override
    public ConsultationResponse reply(Long id, ConsultationReplyRequest req) {
        Consultation c = repo.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Consulta no encontrada"));

        c.setMedicResponse(req.getResponse());
        c.setStatus("CLOSED");
        c.setRespondedAt(Instant.now());

        repo.save(c);
        return toResponse(c);
    }

    @Override
    public ConsultationResponse getById(Long id) {
        Consultation c = repo.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Consulta no encontrada"));
        return toResponse(c);
    }

    @Override
    public List<ConsultationResponse> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<ConsultationResponse> getByMedic(Long medicId) {
        return repo.findByMedicId(medicId).stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private ConsultationResponse toResponse(Consultation c) {
        ConsultationResponse r = new ConsultationResponse();
        r.setId(c.getId());
        r.setPatientId(c.getPatientId());
        r.setMedicId(c.getMedicId());
        r.setMessage(c.getMessage());
        r.setPriority(c.getPriority());
        r.setStatus(c.getStatus());
        r.setMedicResponse(c.getMedicResponse());
        r.setSentAt(c.getSentAt());
        r.setRespondedAt(c.getRespondedAt());
        return r;
    }
}
