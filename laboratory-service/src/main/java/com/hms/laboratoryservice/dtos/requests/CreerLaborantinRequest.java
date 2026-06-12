package com.hms.laboratoryservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreerLaborantinRequest(

    @NotNull(message = "L'identifiant utilisateur est obligatoire")
    UUID userId,

    @NotBlank(message = "Le nom est obligatoire")
    String nom,

    @NotBlank(message = "Le prénom est obligatoire")
    String prenom,

    String email,
    String numeroDeTelephone,

    @NotNull(message = "Le service est obligatoire")
    UUID serviceId

) {}
