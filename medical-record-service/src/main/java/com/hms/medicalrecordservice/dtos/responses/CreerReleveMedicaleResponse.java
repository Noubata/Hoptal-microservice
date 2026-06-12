package com.hms.medicalrecordservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreerReleveMedicaleResponse(
        UUID releveId,
        UUID patientId,
        UUID docteurId,
        String diagnostic,
        String symptomes,
        String notes,
        String typeVisite,
        Integer dureeConsultation,
        LocalDateTime dateDeVisite,
        UUID hopitalId
) {}
