package cl.ufro.dci.cardiocare.resource.service;

import cl.ufro.dci.cardiocare.resource.dto.*;

import java.util.List;

public interface ResourceService {
    ResourceResponse create(ResourceRequest request);
    ResourceResponse getById(Long id);
    List<ResourceResponse> getAll();
    List<ResourceResponse> getByCategory(String category);
    void delete(Long id);
}
