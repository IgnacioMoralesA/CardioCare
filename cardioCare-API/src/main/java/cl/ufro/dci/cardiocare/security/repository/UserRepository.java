package cl.ufro.dci.cardiocare.security.repository;

import cl.ufro.dci.cardiocare.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
