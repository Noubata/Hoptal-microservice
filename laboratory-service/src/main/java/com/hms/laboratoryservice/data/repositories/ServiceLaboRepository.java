package com.hms.laboratoryservice.data.repositories;

import com.hms.laboratoryservice.data.models.ServiceLabo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceLaboRepository
        extends JpaRepository<ServiceLabo, UUID> {

    List<ServiceLabo> findByHopitalId(UUID hopitalId);

    Optional<ServiceLabo> findByServiceIdAndHopitalId(
            UUID serviceId, UUID hopitalId);
    Optional<ServiceLabo> findByNomAndHopitalId(
            String nom, UUID hopitalId);
}
