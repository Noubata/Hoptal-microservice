package com.hms.laboratoryservice.dtos.responses;

import java.util.UUID;

public record ServiceLaboResponse(
    UUID serviceId,
    String nom,
    String description,
    UUID hopitalId
) {}
