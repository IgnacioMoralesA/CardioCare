package cl.ufro.dci.cardiocare.dashboard.service;

import cl.ufro.dci.cardiocare.dashboard.dto.*;

import java.util.List;

public interface DashboardItemService {
    DashboardItemResponse create(DashboardItemRequest request);
    List<DashboardItemResponse> getByOwner(Long ownerId);
    void delete(Long id);
}
