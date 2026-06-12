package com.hms.doctorservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record CreerSpecialiteRequest(

        @NotBlank(message = "Le nom de la spécialité est obligatoire")
        String nom

) {}
