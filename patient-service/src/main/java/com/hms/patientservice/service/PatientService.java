package com.hms.patientservice.service;

import com.hms.patientservice.dtos.requests.AjouterAllergieRequest;
import com.hms.patientservice.dtos.requests.AjouterAntecedentRequest;
import com.hms.patientservice.dtos.requests.CreerPatientRequest;
import com.hms.patientservice.dtos.responses.AjouterAllergieResponse;
import com.hms.patientservice.dtos.responses.AjouterAntecedentResponse;
import com.hms.patientservice.dtos.responses.CreerPatientResponse;
import com.hms.patientservice.dtos.responses.DossierCompletResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    @Transactional
    CreerPatientResponse creerPatient(
            CreerPatientRequest request,
            UUID hopitalId);

    DossierCompletResponse getDossierComplet(
            UUID patientId,
            UUID hopitalId);

    List<CreerPatientResponse> rechercherPatients(
            UUID hopitalId,
            String recherche);

    List<CreerPatientResponse> getPatientsRecents(UUID hopitalId);

    CreerPatientResponse getPatientById(
            UUID patientId,
            UUID hopitalId);

    CreerPatientResponse getPatientByDossier(
            String numeroDossier,
            UUID hopitalId);

    @Transactional
    AjouterAllergieResponse ajouterAllergie(
            UUID patientId,
            UUID hopitalId,
            AjouterAllergieRequest request);

    @Transactional
    AjouterAntecedentResponse ajouterAntecedent(
            UUID patientId,
            UUID hopitalId,
            AjouterAntecedentRequest request);

    List<AjouterAllergieResponse> getAllergies(
            UUID patientId,
            UUID hopitalId);

    List<AjouterAntecedentResponse> getAntecedents(
            UUID patientId,
            UUID hopitalId);
}
