package com.hms.laboratoryservice.service;

import com.hms.laboratoryservice.dtos.requests.*;
import com.hms.laboratoryservice.dtos.responses.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface LaborantinService {

    @Transactional
    CreerLaborantinResponse creerLaborantin(
            CreerLaborantinRequest request,
            UUID hopitalId);

    @Transactional
    DemandeAnalyseResponse creerDemandeAnalyse(
            CreerDemandeAnalyseRequest request,
            UUID hopitalId);

    List<DemandeAnalyseResponse> getDemandesEnAttente(
            UUID laborantinId,
            UUID hopitalId);

    List<CreerLaborantinResponse> getAllLaborantins(UUID hopitalId);

    List<ServiceLaboResponse> getAllServices(UUID hopitalId);

    @Transactional
    ServiceLaboResponse creerService(
            CreerServiceLaboRequest request,
            UUID hopitalId);

    List<DemandeAnalyseResponse> getDemandesParPatient(
            UUID patientId,
            UUID hopitalId);
}
