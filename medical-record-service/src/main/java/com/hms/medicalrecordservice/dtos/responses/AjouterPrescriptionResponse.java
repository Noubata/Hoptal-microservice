package com.hms.medicalrecordservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record AjouterPrescriptionResponse(
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
        UUID hopitalId
) {}
