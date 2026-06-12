package com.hms.laboratoryservice.data.repositories;

import com.hms.laboratoryservice.data.models.Laborantin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaborantinRepository
        extends JpaRepository<Laborantin, UUID> {

    List<Laborantin> findByHopitalId(UUID hopitalId);

    Optional<Laborantin> findByLaborantinIdAndHopitalId(
            UUID laborantinId, UUID hopitalId);

    boolean existsByUserIdAndHopitalId(UUID userId, UUID hopitalId);
}
