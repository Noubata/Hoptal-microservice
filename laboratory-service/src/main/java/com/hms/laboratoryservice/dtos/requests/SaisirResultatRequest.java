package com.hms.laboratoryservice.dtos.requests;

public record SaisirResultatRequest(
        String resultat,
        String valeurNormale,
        String unite,
        String commentaire
) {}
