package cl.ufro.dci.cardiocare.medic.service;

import cl.ufro.dci.cardiocare.medic.dto.*;

import java.util.List;

public interface MedicService {
    MedicResponse create(MedicRequest request);
    MedicResponse getById(Long id);
    List<MedicResponse> getAll();
    void delete(Long id);
}
