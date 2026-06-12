package com.hms.resultlaboservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResultatLaboResponse(
        UUID resultatId,
        UUID demandeId,
        UUID patientId,
        UUID laborantinId,
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
