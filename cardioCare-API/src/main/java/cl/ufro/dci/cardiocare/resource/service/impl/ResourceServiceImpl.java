package cl.ufro.dci.cardiocare.resource.service.impl;

import cl.ufro.dci.cardiocare.resource.dto.*;
import cl.ufro.dci.cardiocare.resource.domain.Resource;
import cl.ufro.dci.cardiocare.resource.repository.ResourceRepository;
import cl.ufro.dci.cardiocare.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository repo;

    @Override
    public ResourceResponse create(ResourceRequest req) {
        Resource r = new Resource();
        r.setTitle(req.getTitle());
        r.setCategory(req.getCategory());
        r.setContent(req.getContent());
        r.setUrl(req.getUrl());
        r.setPublicationDate(LocalDate.now());
        r.setAuthor(req.getAuthor());
        r.setTags(req.getTags());

        repo.save(r);
        return toResponse(r);
    }

    @Override
    public ResourceResponse getById(Long id) {
        Resource r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return toResponse(r);
    }

    @Override
    public List<ResourceResponse> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ResourceResponse> getByCategory(String category) {
        return repo.findByCategory(category).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private ResourceResponse toResponse(Resource r) {
        ResourceResponse dto = new ResourceResponse();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setCategory(r.getCategory());
        dto.setContent(r.getContent());
        dto.setUrl(r.getUrl());
        dto.setPublicationDate(r.getPublicationDate());
        dto.setAuthor(r.getAuthor());
        dto.setTags(r.getTags());
        return dto;
    }
}
