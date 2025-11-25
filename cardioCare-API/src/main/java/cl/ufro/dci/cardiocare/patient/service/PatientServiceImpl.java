package cl.ufro.dci.cardiocare.patient.service;

import cl.ufro.dci.cardiocare.patient.domain.Patient;
import cl.ufro.dci.cardiocare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repo;

    @Override
    public List<Patient> getAll(){
        return repo.findAll();
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
