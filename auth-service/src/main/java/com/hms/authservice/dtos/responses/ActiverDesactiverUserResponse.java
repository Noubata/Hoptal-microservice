package com.hms.authservice.dtos.responses;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActiverDesactiverUserResponse(
        UUID userId,
        boolean active,
        String nomUtilisateur,
        String role,
        LocalDateTime dateMiseAJour
) {}
