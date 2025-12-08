package cl.ufro.dci.cardiocare.medicalRecord.service;

import cl.ufro.dci.cardiocare.medicalRecord.dto.*;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordResponse create(MedicalRecordRequest request);

    List<MedicalRecordResponse> getHistory(Long patientId);

    List<MedicalRecordResponse> getByPatient(Long patientId);
    void delete(Long id);

}
