package com.hms.patientservice.data.repositories;

import com.hms.patientservice.data.models.Allergie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AllergieRepository extends JpaRepository<Allergie, UUID> {
    List<Allergie> findByPatientPatientId(UUID patientId);
}
