package com.hms.resultlaboservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaisirResultatRequest(

        @NotNull(message = "Le patient est obligatoire")
        UUID patientId,

        @NotNull(message = "Le laborantin est obligatoire")
        UUID laborantinId,

        @NotBlank(message = "Le type d'analyse est obligatoire")
        String typeAnalyse,

        @NotBlank(message = "Le résultat est obligatoire")
        String resultat,

        @NotBlank(message = "La valeur normale est obligatoire")
        String valeurNormale,

        String unite,
        String commentaire

) {}
