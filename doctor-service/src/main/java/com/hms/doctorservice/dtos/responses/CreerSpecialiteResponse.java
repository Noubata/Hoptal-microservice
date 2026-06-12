package com.hms.doctorservice.dtos.responses;

import java.util.UUID;

public record CreerSpecialiteResponse(
        UUID specialiteId,
        String nom,
        UUID hopitalId
) {}
