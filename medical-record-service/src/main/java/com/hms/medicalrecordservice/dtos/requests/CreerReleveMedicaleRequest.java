package com.hms.medicalrecordservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreerReleveMedicaleRequest(

        @NotNull(message = "Le patient est obligatoire")
        UUID patientId,
        @NotNull(message = "Le médecin est obligatoire")
        UUID docteurId,
        @NotBlank(message = "Le diagnostic est obligatoire")
        String diagnostic,
        String symptomes,
        String notes,
        String typeVisite,
        Integer dureeConsultation

) {}
