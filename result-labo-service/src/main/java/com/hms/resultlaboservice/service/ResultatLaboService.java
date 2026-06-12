package com.hms.resultlaboservice.service;

import com.hms.resultlaboservice.dtos.requests.CreerDemandeAnalyseRequest;
import com.hms.resultlaboservice.dtos.requests.SaisirResultatRequest;
import com.hms.resultlaboservice.dtos.responses.ResultatLaboResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ResultatLaboService {

    @Transactional
    ResultatLaboResponse saisirResultat(
            UUID demandeId,
            UUID hopitalId,
            SaisirResultatRequest request);

    List<ResultatLaboResponse> getResultatsParPatient(
            UUID patientId,
            UUID hopitalId);

    List<ResultatLaboResponse> getResultatsParLaborantin(
            UUID laborantinId,
            UUID hopitalId);

    ResultatLaboResponse getResultatParDemande(
            UUID demandeId,
            UUID hopitalId);

    List<ResultatLaboResponse> getResultatsAvecAnomalie(
            UUID hopitalId);
}
