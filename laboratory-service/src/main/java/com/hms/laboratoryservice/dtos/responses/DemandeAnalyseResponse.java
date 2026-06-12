package com.hms.laboratoryservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record DemandeAnalyseResponse(
        UUID demandeId,
        UUID patientId,
        UUID laborantinId,
        String typeAnalyse,
        String statut,
        UUID hopitalId,
        LocalDateTime dateCreation
) {}
