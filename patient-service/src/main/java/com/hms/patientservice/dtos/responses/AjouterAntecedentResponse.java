package com.hms.patientservice.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record AjouterAntecedentResponse(
        UUID antecedentId,
        String typeAntecedent,
        String description,
        LocalDate dateDebut,
        boolean chronique,
        String notes
) {}
