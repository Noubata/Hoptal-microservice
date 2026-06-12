package com.hms.patientservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AjouterAllergieRequest(

        @NotBlank(message = "La substance est obligatoire")
        String substance,

        @NotBlank(message = "La sévérité est obligatoire")
        String severite,

        String reaction,
        LocalDate dateDecouverte

) {}
