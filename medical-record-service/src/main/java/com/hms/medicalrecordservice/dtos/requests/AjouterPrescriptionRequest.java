package com.hms.medicalrecordservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AjouterPrescriptionRequest(

        @NotBlank(message = "Le médicament est obligatoire")
        String nomDuMedicament,
        @NotBlank(message = "Le dosage est obligatoire")
        String dosage,
        @NotBlank(message = "La fréquence est obligatoire")
        String frequence,
        @NotBlank(message = "La durée est obligatoire")
        String duree,
        String instructions,
        boolean renouvellable

) {}
