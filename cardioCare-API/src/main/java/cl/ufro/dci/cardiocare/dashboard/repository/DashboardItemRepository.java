package cl.ufro.dci.cardiocare.dashboard.repository;

import cl.ufro.dci.cardiocare.dashboard.domain.DashboardItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardItemRepository extends JpaRepository<DashboardItem, Long> {
    List<DashboardItem> findByOwnerId(Long ownerId);
}

