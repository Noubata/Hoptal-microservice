package com.hms.authservice.dtos.responses;

import java.util.UUID;

public record LoginResponse(
        String token,
        String role,
        String nomUtilisateur,
        UUID userId,
        UUID hopitalId) {
}
