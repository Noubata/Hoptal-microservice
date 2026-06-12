package com.hms.medicalrecordservice.data.repositories;

import com.hms.medicalrecordservice.data.models.ReleveMedicale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReleveMedicaleRepository
        extends JpaRepository<ReleveMedicale, UUID> {
    List<ReleveMedicale> findByPatientIdAndHopitalId(
            UUID patientId, UUID hopitalId);
    List<ReleveMedicale> findByDocteurIdAndHopitalId(
            UUID docteurId, UUID hopitalId);
    Optional<ReleveMedicale> findByReleveIdAndHopitalId(
            UUID releveId, UUID hopitalId);
    List<ReleveMedicale> findByHopitalId(UUID hopitalId);
}
