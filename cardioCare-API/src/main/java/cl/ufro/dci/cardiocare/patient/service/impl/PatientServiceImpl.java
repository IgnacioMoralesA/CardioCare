package cl.ufro.dci.cardiocare.patient.service.impl;

import cl.ufro.dci.cardiocare.patient.dto.*;
import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import cl.ufro.dci.cardiocare.patient.service.PatientService;
import cl.ufro.dci.cardiocare.security.domain.User;
import cl.ufro.dci.cardiocare.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Override
    public PatientResponse create(PatientRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setGender(request.getGender());
        patient.setBirthDate(request.getBirthDate());
        patient.setMedicalCondition(request.getMedicalCondition());
        patient.setSurgeryDate(request.getSurgeryDate());

        patientRepository.save(patient);

        return toResponse(patient);
    }

    @Override
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return toResponse(patient);
    }

    @Override
    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    private PatientResponse toResponse(Patient p) {
        PatientResponse r = new PatientResponse();
        r.setId(p.getId());
        r.setName(p.getUser().getName());
        r.setEmail(p.getUser().getEmail());
        r.setGender(p.getGender());
        r.setBirthDate(p.getBirthDate());
        r.setSurgeryDate(p.getSurgeryDate());
        r.setMedicalCondition(p.getMedicalCondition());
        return r;
    }
}
