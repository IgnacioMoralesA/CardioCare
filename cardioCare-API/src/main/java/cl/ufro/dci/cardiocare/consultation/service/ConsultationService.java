package cl.ufro.dci.cardiocare.consultation.service;

import cl.ufro.dci.cardiocare.consultation.dto.*;

import java.util.List;

public interface ConsultationService {
    ConsultationResponse create(ConsultationRequest request);
    ConsultationResponse reply(Long id, ConsultationReplyRequest request);
    ConsultationResponse getById(Long id);
    List<ConsultationResponse> getByPatient(Long patientId);
    List<ConsultationResponse> getByMedic(Long medicId);
    void delete(Long id);
}
