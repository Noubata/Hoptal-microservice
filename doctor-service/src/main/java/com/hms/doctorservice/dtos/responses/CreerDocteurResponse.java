package com.hms.doctorservice.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

// CreerDocteurResponse.java
public record CreerDocteurResponse(
        UUID docteurId,
        String nom,
        String prenom,
        String numeroDeLicence,
        String email,
        String specialite,
        String departement,
        UUID hopitalId,
        LocalDate dateEmbauche
) {}
