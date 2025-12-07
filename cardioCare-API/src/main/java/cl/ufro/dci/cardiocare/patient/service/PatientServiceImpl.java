package cl.ufro.dci.cardiocare.patient.service;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;      // IMPORTANTE
import org.springframework.data.domain.Pageable;  // IMPORTANTE
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repo;

    @Override
    public Page<Patient> getAll(Pageable pageable){
        // JpaRepository ya sabe cómo manejar el objeto Pageable
        return repo.findAll(pageable);
    }

    @Override
    public Patient getById(Long id){
        return repo.findById(id).orElse(null);
    }

    @Override
    public Patient save(Patient patient) {
        return repo.save(patient);
    }
}