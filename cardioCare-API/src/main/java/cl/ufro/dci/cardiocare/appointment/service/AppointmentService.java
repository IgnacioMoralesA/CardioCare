package cl.ufro.dci.cardiocare.appointment.service;

import cl.ufro.dci.cardiocare.appointment.dto.*;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse create(AppointmentRequest request);
    AppointmentResponse updateStatus(Long id, AppointmentUpdateStatusRequest request);
    AppointmentResponse getById(Long id);
    List<AppointmentResponse> getByPatient(Long patientId);
    List<AppointmentResponse> getByMedic(Long medicId);
    void delete(Long id);
}
