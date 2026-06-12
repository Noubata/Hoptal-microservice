package com.hms.patientservice.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record AjouterAllergieResponse(
        UUID allergieId,
        String substance,
        String severite,
        String reaction,
        LocalDate dateDecouverte
) {}
