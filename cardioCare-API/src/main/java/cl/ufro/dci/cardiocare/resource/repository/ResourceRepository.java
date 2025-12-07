package cl.ufro.dci.cardiocare.resource.repository;

import cl.ufro.dci.cardiocare.resource.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCategory(String category);
    List<Resource> findByAuthor(String author);
}
