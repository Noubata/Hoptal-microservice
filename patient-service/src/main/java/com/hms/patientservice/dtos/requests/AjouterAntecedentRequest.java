package com.hms.patientservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AjouterAntecedentRequest(

        @NotBlank(message = "Le type est obligatoire")
        String typeAntecedent,

        @NotBlank(message = "La description est obligatoire")
        String description,

        LocalDate dateDebut,
        boolean chronique,
        String notes

) {}
