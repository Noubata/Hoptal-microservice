package com.hms.doctorservice.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

// CreerDocteurRequest.java
public record CreerDocteurRequest(

        @NotNull(message = "L'identifiant utilisateur est obligatoire")
        UUID userId,

        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,

        @NotBlank(message = "Le numéro de licence est obligatoire")
        String numeroDeLicence,

        @Email(message = "Format email invalide")
        String email,

        String numeroDeTelephone,

        @NotNull(message = "La date d'embauche est obligatoire")
        LocalDate dateEmbauche,

        @NotNull(message = "La spécialité est obligatoire")
        UUID specialiteId,

        @NotNull(message = "Le département est obligatoire")
        UUID departementId

) {}