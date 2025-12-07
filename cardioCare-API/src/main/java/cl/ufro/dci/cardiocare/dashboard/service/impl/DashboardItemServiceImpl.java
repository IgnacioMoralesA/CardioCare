package cl.ufro.dci.cardiocare.dashboard.service.impl;

import cl.ufro.dci.cardiocare.dashboard.dto.*;
import cl.ufro.dci.cardiocare.dashboard.domain.DashboardItem;
import cl.ufro.dci.cardiocare.dashboard.repository.DashboardItemRepository;
import cl.ufro.dci.cardiocare.dashboard.service.DashboardItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardItemServiceImpl implements DashboardItemService {

    private final DashboardItemRepository repo;

    @Override
    public DashboardItemResponse create(DashboardItemRequest req) {
        DashboardItem di = new DashboardItem();
        di.setWidgetName(req.getWidgetName());
        di.setDataJson(req.getDataJson());
        di.setOwnerId(req.getOwnerId());

        repo.save(di);
        return toResponse(di);
    }

    @Override
    public List<DashboardItemResponse> getByOwner(Long ownerId) {
        return repo.findByOwnerId(ownerId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private DashboardItemResponse toResponse(DashboardItem di) {
        DashboardItemResponse r = new DashboardItemResponse();
        r.setId(di.getId());
        r.setWidgetName(di.getWidgetName());
        r.setDataJson(di.getDataJson());
        r.setOwnerId(di.getOwnerId());
        return r;
    }
}
