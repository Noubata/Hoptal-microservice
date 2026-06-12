package com.hms.prescriptionservice.dtos.responses;

import java.util.List;
import java.util.UUID;

public record VerifierAllergieResponse(
        UUID patientId,
        String nomDuMedicament,
        boolean allergiDetectee,
        List<String> substancesConflits
) {}
