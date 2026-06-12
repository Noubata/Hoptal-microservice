package com.hms.authservice.data.repositories;

import com.hms.authservice.data.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByNom(String nom);
    boolean existsByNom(String nom);
}
