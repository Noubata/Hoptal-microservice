package com.hms.prescriptionservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VerifierAllergieRequest(

        @NotNull(message = "Le patient est obligatoire")
        UUID patientId,

        @NotBlank(message = "Le médicament est obligatoire")
        String nomDuMedicament

) {}
