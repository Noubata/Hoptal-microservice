package com.hms.authservice.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EnregistrerUtilisateurRequest(

        @NotBlank(message = "Le nom d'utilisateur est obligatoire")
        String nomUtilisateur,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format email invalide")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
        String motDePasse,

        @NotBlank(message = "Le numéro de téléphone est obligatoire")
        String numeroTelephone,

        @NotBlank(message = "Le quartier est obligatoire")
        String quartier,

        @NotNull(message = "Le rôle est obligatoire")
        UUID roleId

) {}
