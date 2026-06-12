package com.hms.prescriptionservice.service;

import com.hms.prescriptionservice.client.MedicalRecordClient;
import com.hms.prescriptionservice.dtos.requests.VerifierAllergieRequest;
import com.hms.prescriptionservice.dtos.responses.PrescriptionActiveResponse;
import com.hms.prescriptionservice.dtos.responses.VerifierAllergieResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final MedicalRecordClient medicalRecordClient;

    @Override
    public List<PrescriptionActiveResponse> getPrescriptionsActives(
            UUID patientId,
            UUID hopitalId) {

        List<PrescriptionActiveResponse> toutes =
                medicalRecordClient.getPrescriptionsPatient(
                        patientId, hopitalId);

        return toutes.stream()
                .filter(p -> {
                    if (p.duree() == null ||
                            p.dateDePrescription() == null) return false;

                    long jours = parseDureeEnJours(
                            p.duree().toLowerCase());

                    LocalDateTime expiration =
                            p.dateDePrescription().plusDays(jours);

                    return LocalDateTime.now().isBefore(expiration);
                })
                .toList();
    }

    @Override
    public VerifierAllergieResponse verifierAllergies(
            VerifierAllergieRequest request,
            UUID hopitalId) {

        List<String> allergies =
                medicalRecordClient.getAllergiesPatient(
                        request.patientId(), hopitalId);

        List<String> conflits = allergies.stream()
                .filter(substance ->
                        request.nomDuMedicament().toLowerCase()
                                .contains(substance.toLowerCase())
                                || substance.toLowerCase()
                                .contains(request.nomDuMedicament().toLowerCase()))
                .toList();

        log.info("Vérification allergie pour patient: {} médicament: {}",
                request.patientId(), request.nomDuMedicament());

        return new VerifierAllergieResponse(
                request.patientId(),
                request.nomDuMedicament(),
                !conflits.isEmpty(),
                conflits
        );
    }

    private long parseDureeEnJours(String duree) {
        try {
            String[] parts = duree.trim().split(" ");
            long nombre = Long.parseLong(parts[0]);
            if (duree.contains("mois")) return nombre * 30;
            if (duree.contains("semaine")) return nombre * 7;
            return nombre;
        } catch (Exception e) {
            log.warn("Impossible de parser la durée: {}", duree);
            return 7;
        }
    }
}
