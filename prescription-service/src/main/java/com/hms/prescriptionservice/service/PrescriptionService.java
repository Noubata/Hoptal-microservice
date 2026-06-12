package com.hms.prescriptionservice.service;

import com.hms.prescriptionservice.dtos.requests.VerifierAllergieRequest;
import com.hms.prescriptionservice.dtos.responses.PrescriptionActiveResponse;
import com.hms.prescriptionservice.dtos.responses.VerifierAllergieResponse;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {
    List<PrescriptionActiveResponse> getPrescriptionsActives(
            UUID patientId,
            UUID hopitalId);

    VerifierAllergieResponse verifierAllergies(
            VerifierAllergieRequest request,
            UUID hopitalId);
}
