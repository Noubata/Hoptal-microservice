package com.hms.patientservice.data.repositories;

import com.hms.patientservice.data.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findByHopitalId(UUID hopitalId);

    List<Patient> findByHopitalIdAndNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
            UUID hopitalId, String nom, String prenom);

    List<Patient> findByHopitalIdAndNumeroDeTelephone(
            UUID hopitalId, String numeroDeTelephone);

    Optional<Patient> findByPatientIdAndHopitalId(
            UUID patientId, UUID hopitalId);

    Optional<Patient> findByNumeroDossierAndHopitalId(
            String numeroDossier, UUID hopitalId);

    List<Patient> findTop5ByHopitalIdOrderByDateCreationDesc(
            UUID hopitalId);

    long countByHopitalId(UUID hopitalId);
}
