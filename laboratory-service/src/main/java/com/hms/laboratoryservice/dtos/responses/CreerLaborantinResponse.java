package com.hms.laboratoryservice.dtos.responses;

import java.util.UUID;

public record CreerLaborantinResponse(
    UUID laborantinId,
    String nom,
    String prenom,
    String email,
    String nomService,
    UUID hopitalId
) {}
