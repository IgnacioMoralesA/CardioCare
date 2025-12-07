package cl.ufro.dci.cardiocare.resource.service;

import cl.ufro.dci.cardiocare.resource.dto.*;

import java.util.List;

public interface ResourceProgressService {
    ResourceProgressResponse updateProgress(ResourceProgressRequest request);
    List<ResourceProgressResponse> getByPatient(Long patientId);
}
