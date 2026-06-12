package com.hms.doctorservice.dtos.responses;

import java.util.UUID;

public record CreerDepartementResponse(
        UUID departementId,
        String nom,
        String description,
        UUID hopitalId
) {}
