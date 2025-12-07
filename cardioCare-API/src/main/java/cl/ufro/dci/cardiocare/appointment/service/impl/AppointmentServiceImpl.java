package cl.ufro.dci.cardiocare.appointment.service.impl;

import cl.ufro.dci.cardiocare.appointment.dto.*;
import cl.ufro.dci.cardiocare.appointment.domain.Appointment;
import cl.ufro.dci.cardiocare.appointment.repository.AppointmentRepository;
import cl.ufro.dci.cardiocare.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponse create(AppointmentRequest request) {

        Appointment a = new Appointment();
        a.setPatientId(request.getPatientId());
        a.setMedicId(request.getMedicId());
        a.setDateTime(request.getDateTime());
        a.setStatus("PENDING");
        a.setModality(request.getModality());
        a.setNotes(request.getNotes());

        appointmentRepository.save(a);
        return toResponse(a);
    }

    @Override
    public AppointmentResponse updateStatus(Long id, AppointmentUpdateStatusRequest req) {
        Appointment ap = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        ap.setStatus(req.getStatus());
        appointmentRepository.save(ap);
        return toResponse(ap);
    }

    @Override
    public AppointmentResponse getById(Long id) {
        Appointment ap = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return toResponse(ap);
    }

    @Override
    public List<AppointmentResponse> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getByMedic(Long medicId) {
        return appointmentRepository.findByMedicId(medicId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentResponse toResponse(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setId(a.getId());
        r.setPatientId(a.getPatientId());
        r.setMedicId(a.getMedicId());
        r.setDateTime(a.getDateTime());
        r.setStatus(a.getStatus());
        r.setModality(a.getModality());
        r.setNotes(a.getNotes());
        return r;
    }
}
