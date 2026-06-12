package com.hms.patientservice.dtos.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreerPatientResponse(
        UUID patientId,
        String numeroDossier,
        String nom,
        String prenom,
        LocalDate dateDeNaissance,
        String sexe,
        String groupeSanguin,
        String statutPatient,
        String numeroDeTelephone,
        String email,
        UUID hopitalId,
        LocalDateTime dateCreation
) {}
