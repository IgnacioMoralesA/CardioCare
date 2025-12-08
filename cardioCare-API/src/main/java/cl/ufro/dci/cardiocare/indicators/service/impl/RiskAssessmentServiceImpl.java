package cl.ufro.dci.cardiocare.indicators.service.impl;

import cl.ufro.dci.cardiocare.consultation.domain.Consultation;
import cl.ufro.dci.cardiocare.consultation.repository.ConsultationRepository;
import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.indicators.service.RiskAssessmentService;
import cl.ufro.dci.cardiocare.notification.domain.Notification;
import cl.ufro.dci.cardiocare.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final NotificationRepository notificationRepo;
    private final ConsultationRepository consultationRepo;

    @Override
    public void evaluateRisk(Indicator indicator) {
        if (indicator == null || indicator.getValue() == null)
            return;

        double value = Double.parseDouble(String.valueOf(indicator.getValue()));
        String type = indicator.getType().toLowerCase();

        boolean isHighRisk = false;
        String message = "";

        // Minimalistic Threshold Logic for Demo
        if (type.contains("presion") || type.contains("pressure")) { // Blood Pressure
            if (value > 160) {
                isHighRisk = true;
                message = "ALERTA: Presión arterial CRÍTICA detectada (" + value + "). Acudir a urgencias.";
            } else if (value > 140) {
                message = "Advertencia: Presión arterial alta (" + value + "). Consultar médico.";
                createNotification(indicator.getMedicalRecord().getPatient().getUser().getId(), message);
            }
        } else if (type.contains("saturacion") || type.contains("oxygen")) { // Oxygen
            if (value < 90) {
                isHighRisk = true;
                message = "ALERTA: Saturación de oxígeno BAJA (" + value + "%). Atención inmediata requerida.";
            }
        }

        if (isHighRisk) {
            // 1. Notify Patient
            createNotification(indicator.getMedicalRecord().getPatient().getUser().getId(), message);

            // 2. Auto-create Consultation for Medic
            createUrgentConsultation(indicator, message);
        }
    }

    private void createNotification(Long userId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setType("RISK_ALERT");
        n.setMessage(message);
        n.setChannel("APP");
        n.setSentAt(Instant.now());
        n.setReadFlag(false);
        notificationRepo.save(n);
    }

    private void createUrgentConsultation(Indicator indicator, String message) {
        Consultation c = new Consultation();
        c.setPatient(indicator.getMedicalRecord().getPatient());
        // Medic is left null or assigned to a default triage medic if logic existed
        c.setMessage("AUTO-GENERATED: " + message);
        c.setPriority("URGENT");
        c.setStatus("OPEN");
        c.setSentAt(Instant.now());
        consultationRepo.save(c);
    }
}
