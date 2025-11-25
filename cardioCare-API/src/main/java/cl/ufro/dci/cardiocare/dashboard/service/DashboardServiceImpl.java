package cl.ufro.dci.cardiocare.dashboard.service;

import cl.ufro.dci.cardiocare.message.repository.MessageRepository;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PatientRepository patientRepo;
    private final MessageRepository messageRepo;

    @Override
    public long getPacientesActivos() {
        return patientRepo.count();
    }

    @Override
    public long getConsultasPendientes() {
        return messageRepo.findAll().stream().filter(m -> !m.isLeido()).count();
    }

    @Override
    public double getSatisfaccionGeneral() {
        return 4.6; // hardcoded demo
    }

    @Override
    public long getAlertasCriticas() {
        return messageRepo.findAll().stream().filter(m -> "Alta".equals(m.getPrioridad())).count();
    }
}

