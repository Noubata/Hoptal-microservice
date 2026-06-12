package com.hms.medicalrecordservice.data.repositories;

import com.hms.medicalrecordservice.data.models.Prescription;
import com.hms.medicalrecordservice.data.models.StatutPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByPatientIdAndHopitalId(
            UUID patientId, UUID hopitalId);
    List<Prescription> findByReleveMedicaleReleveIdAndHopitalId(
            UUID releveId, UUID hopitalId);
    List<Prescription> findByPatientIdAndHopitalIdAndStatut(
            UUID patientId, UUID hopitalId, StatutPrescription statut);
    Optional<Prescription> findByPrescriptionIdAndHopitalId(
            UUID prescriptionId, UUID hopitalId);
}
