package com.hms.prescriptionservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrescriptionActiveResponse(
        UUID prescriptionId,
        UUID patientId,
        UUID releveId,
        String nomDuMedicament,
        String dosage,
        String frequence,
        String duree,
        String instructions,
        boolean renouvellable,
        String statut,
        LocalDateTime dateDePrescription,
        LocalDateTime dateExpiration,
        UUID hopitalId
) {}
