package com.hms.doctorservice.data.repositories;

import com.hms.doctorservice.data.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartementRepository extends JpaRepository<Departement, UUID> {
    Optional<Departement> findByNomAndHopitalId(
            String nom, UUID hopitalId);
}
