package cl.ufro.dci.cardiocare.appointment.repository;

import cl.ufro.dci.cardiocare.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByMedicId(Long medicId);
}
