package com.hms.laboratoryservice.data.repositories;

import com.hms.laboratoryservice.data.models.DemandeAnalyse;
import com.hms.laboratoryservice.data.models.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemandeAnalyseRepository
        extends JpaRepository<DemandeAnalyse, UUID> {

    // Get all demands for a specific laborantin filtered by status
    List<DemandeAnalyse> findByLaborantinIdAndHopitalIdAndStatut(
            UUID laborantinId,
            UUID hopitalId,
            StatutDemande statut);

    // Get all demands for a specific patient
    List<DemandeAnalyse> findByPatientIdAndHopitalId(
            UUID patientId,
            UUID hopitalId);

    // Get all demands for a hospital
    List<DemandeAnalyse> findByHopitalId(UUID hopitalId);

    // Get specific demand scoped to hospital
    Optional<DemandeAnalyse> findByDemandeIdAndHopitalId(
            UUID demandeId,
            UUID hopitalId);

    // Check if demand exists for a patient and laborantin
    boolean existsByPatientIdAndLaborantinIdAndHopitalIdAndStatut(
            UUID patientId,
            UUID laborantinId,
            UUID hopitalId,
            StatutDemande statut);

    // Get all demands by status for a hospital
    List<DemandeAnalyse> findByHopitalIdAndStatut(
            UUID hopitalId,
            StatutDemande statut);
}
