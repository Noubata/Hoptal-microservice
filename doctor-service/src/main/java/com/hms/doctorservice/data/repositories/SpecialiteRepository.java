package com.hms.doctorservice.data.repositories;

import com.hms.doctorservice.data.models.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpecialiteRepository extends JpaRepository<Specialite, UUID> {
    Optional<Specialite> findByNomAndHopitalId(
            String nom, UUID hopitalId);
}
