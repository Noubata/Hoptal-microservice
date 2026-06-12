package com.hms.medicalrecordservice.service;

import com.hms.medicalrecordservice.dtos.requests.AjouterPrescriptionRequest;
import com.hms.medicalrecordservice.dtos.requests.CreerReleveMedicaleRequest;
import com.hms.medicalrecordservice.dtos.responses.AjouterPrescriptionResponse;
import com.hms.medicalrecordservice.dtos.responses.CreerReleveMedicaleResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface ReleveMedicaleService {
    @Transactional
    CreerReleveMedicaleResponse creerReleve(
            CreerReleveMedicaleRequest request,
            UUID hopitalId);

    @Transactional
    AjouterPrescriptionResponse ajouterPrescription(
            UUID releveId,
            UUID hopitalId,
            AjouterPrescriptionRequest request);

    List<CreerReleveMedicaleResponse> getHistoriquePatient(
            UUID patientId,
            UUID hopitalId);

    List<CreerReleveMedicaleResponse> getRelevesDocteur(
            UUID docteurId,
            UUID hopitalId);

    CreerReleveMedicaleResponse getReleveById(
            UUID releveId,
            UUID hopitalId);

    List<AjouterPrescriptionResponse> getPrescriptionsReleve(
            UUID releveId,
            UUID hopitalId);
}
