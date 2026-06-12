package com.hms.laboratoryservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResultatDuLaboResponse(
        UUID resultatId,
        UUID patientId,
        String typeAnalyse,
        String resultat,
        String valeurNormale,
        String unite,
        String commentaire,
        boolean anomalie,
        String statutResultat,
        LocalDateTime dateDeTest,
        UUID hopitalId
) {}
