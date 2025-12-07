package cl.ufro.dci.cardiocare.resource.service.impl;

import cl.ufro.dci.cardiocare.resource.dto.*;
import cl.ufro.dci.cardiocare.resource.domain.ResourceProgress;
import cl.ufro.dci.cardiocare.resource.repository.ResourceProgressRepository;
import cl.ufro.dci.cardiocare.resource.service.ResourceProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceProgressServiceImpl implements ResourceProgressService {

    private final ResourceProgressRepository repo;

    @Override
    public ResourceProgressResponse updateProgress(ResourceProgressRequest req) {

        ResourceProgress rp = repo.findByPatientIdAndResourceId(req.getPatientId(), req.getResourceId())
                .orElseGet(() -> {
                    ResourceProgress np = new ResourceProgress();
                    np.setPatientId(req.getPatientId());
                    np.setResourceId(req.getResourceId());
                    return np;
                });

        rp.setProgressPercent(req.getProgressPercent());
        rp.setCompleted(req.isCompleted());
        rp.setUpdatedAt(Instant.now());

        repo.save(rp);
        return toResponse(rp);
    }

    @Override
    public List<ResourceProgressResponse> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId).stream()
                .map(this::toResponse)
                .toList();
    }

    private ResourceProgressResponse toResponse(ResourceProgress rp) {
        ResourceProgressResponse r = new ResourceProgressResponse();
        r.setId(rp.getId());
        r.setPatientId(rp.getPatientId());
        r.setResourceId(rp.getResourceId());
        r.setProgressPercent(rp.getProgressPercent());
        r.setCompleted(rp.isCompleted());
        r.setUpdatedAt(rp.getUpdatedAt());
        return r;
    }
}
