package com.hms.doctorservice.data.repositories;

import com.hms.doctorservice.data.models.Docteur;
import com.hms.doctorservice.data.models.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocteurRepository extends JpaRepository<Docteur, UUID> {

    boolean existsByNumeroDeLicenceAndHopitalId(
            String numeroDeLicence, UUID hopitalId);

    Optional<Docteur> findByDocteurIdAndHopitalId(
            UUID docteurId, UUID hopitalId);

    List<Docteur> findByHopitalId(UUID hopitalId);

    List<Docteur> findByHopitalIdAndSpecialite(
            UUID hopitalId, Specialite specialite);
}