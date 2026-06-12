package com.hms.doctorservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record CreerDepartementRequest(

        @NotBlank(message = "Le nom du département est obligatoire")
        String nom,
        String description

) {}
