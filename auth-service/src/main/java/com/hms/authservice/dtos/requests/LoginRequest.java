package com.hms.authservice.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LoginRequest(

        @NotBlank(message = "Le nom d'utilisateur est obligatoire")
        String nomUtilisateur,

        @NotBlank(message = "Le mot de passe est obligatoire")
        String motDePasse,

        @NotNull(message = "L'identifiant de l'hôpital est obligatoire")
        UUID hopitalId

) {}
