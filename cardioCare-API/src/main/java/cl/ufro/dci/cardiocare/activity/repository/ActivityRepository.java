package cl.ufro.dci.cardiocare.activity.repository;

import cl.ufro.dci.cardiocare.activity.domain.Activity;
import cl.ufro.dci.cardiocare.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByPatient(Patient patient);

    List<Activity> findByPatientAndActivityDate(Patient patient, LocalDate date);

    List<Activity> findByActivityDate(LocalDate date);
}
