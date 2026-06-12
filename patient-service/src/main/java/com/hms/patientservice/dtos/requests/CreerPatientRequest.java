package com.hms.patientservice.dtos.requests;

import com.hms.patientservice.data.models.GroupeSanguin;
import com.hms.patientservice.data.models.Sexe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreerPatientRequest(

        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateDeNaissance,

        @NotNull(message = "Le sexe est obligatoire")
        Sexe sexe,

        @NotNull(message = "Le groupe sanguin est obligatoire")
        GroupeSanguin groupeSanguin,

        String email,
        String numeroDeTelephone,
        String adresse,
        String quartier,
        String ville,
        String nomContactUrgence,
        String telephoneContactUrgence,
        String emailContactUrgence

) {}
