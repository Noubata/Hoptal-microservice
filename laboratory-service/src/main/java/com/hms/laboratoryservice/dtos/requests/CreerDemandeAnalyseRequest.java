package com.hms.laboratoryservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreerDemandeAnalyseRequest(

        @NotNull(message = "Le patient est obligatoire")
        UUID patientId,
        @NotNull(message = "Le laborantin est obligatoire")
        UUID laborantinId,
        @NotBlank(message = "Le type d'analyse est obligatoire")
        String typeAnalyse

) {}
