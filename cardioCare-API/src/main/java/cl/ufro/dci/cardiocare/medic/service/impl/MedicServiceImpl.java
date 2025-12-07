package cl.ufro.dci.cardiocare.medic.service.impl;

import cl.ufro.dci.cardiocare.medic.dto.*;
import cl.ufro.dci.cardiocare.medic.domain.Medic;
import cl.ufro.dci.cardiocare.medic.repository.MedicRepository;
import cl.ufro.dci.cardiocare.medic.service.MedicService;
import cl.ufro.dci.cardiocare.security.domain.User;
import cl.ufro.dci.cardiocare.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl implements MedicService {

    private final MedicRepository medicRepository;
    private final UserRepository userRepository;

    @Override
    public MedicResponse create(MedicRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Medic medic = new Medic();
        medic.setUser(user);
        medic.setSpecialty(request.getSpecialty());
        medic.setLicenseNumber(request.getLicenseNumber());
        medic.setScheduleJson(request.getScheduleJson());

        medicRepository.save(medic);

        return toResponse(medic);
    }

    @Override
    public MedicResponse getById(Long id) {
        Medic medic = medicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        return toResponse(medic);
    }

    @Override
    public List<MedicResponse> getAll() {
        return medicRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        medicRepository.deleteById(id);
    }

    private MedicResponse toResponse(Medic m) {
        MedicResponse r = new MedicResponse();
        r.setId(m.getId());
        r.setName(m.getUser().getName());
        r.setEmail(m.getUser().getEmail());
        r.setSpecialty(m.getSpecialty());
        r.setLicenseNumber(m.getLicenseNumber());
        r.setScheduleJson(m.getScheduleJson());
        return r;
    }
}
