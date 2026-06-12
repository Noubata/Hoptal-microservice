package com.hms.patientservice.data.repositories;

import com.hms.patientservice.data.models.Antecedent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AntecedentRepository extends JpaRepository<Antecedent, UUID> {
    List<Antecedent> findByPatientPatientId(UUID patientId);
}
