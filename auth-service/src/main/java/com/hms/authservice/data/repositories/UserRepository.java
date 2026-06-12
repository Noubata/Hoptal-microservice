package com.hms.authservice.data.repositories;

import com.hms.authservice.data.models.Role;
import com.hms.authservice.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNomUtilisateur(String nomUtilisateur);

    Optional<User> findByNomUtilisateurAndHopitalId(
            String nomUtilisateur,
            UUID hopitalId
    );
    boolean existsByNomUtilisateurAndHopitalId(
            String nomUtilisateur,
            UUID hopitalId
    );
    List<User> findByHopitalId(UUID hopitalId);
    List<User> findByHopitalIdAndActive(UUID hopitalId, boolean active);
    List<User> findByHopitalIdAndRole(UUID hopitalId, Role role);
}
