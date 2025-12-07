package cl.ufro.dci.cardiocare.patient.service;

import cl.ufro.dci.cardiocare.patient.dto.*;

import java.util.List;

public interface PatientService {
    PatientResponse create(PatientRequest request);
    PatientResponse getById(Long id);
    List<PatientResponse> getAll();
    void delete(Long id);
}
