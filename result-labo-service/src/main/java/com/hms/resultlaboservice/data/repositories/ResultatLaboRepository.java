package com.hms.resultlaboservice.data.repositories;

import com.hms.resultlaboservice.data.models.ResultatLabo;
import com.hms.resultlaboservice.data.models.StatutResultat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResultatLaboRepository
        extends JpaRepository<ResultatLabo, UUID> {

    List<ResultatLabo> findByPatientIdAndHopitalId(
            UUID patientId, UUID hopitalId);

    List<ResultatLabo> findByLaborantinIdAndHopitalId(
            UUID laborantinId, UUID hopitalId);

    Optional<ResultatLabo> findByDemandeIdAndHopitalId(
            UUID demandeId, UUID hopitalId);

    boolean existsByDemandeIdAndHopitalId(
            UUID demandeId, UUID hopitalId);

    List<ResultatLabo> findByHopitalIdAndAnomalieTrue(
            UUID hopitalId);
}
