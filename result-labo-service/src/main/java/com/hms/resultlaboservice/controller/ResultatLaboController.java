package com.hms.resultlaboservice.controller;

import com.hms.common.security.JwtService;
import com.hms.resultlaboservice.dtos.requests.SaisirResultatRequest;
import com.hms.resultlaboservice.dtos.responses.APIResponse;
import com.hms.resultlaboservice.dtos.responses.ResultatLaboResponse;
import com.hms.resultlaboservice.exceptions.TokenManquantException;
import com.hms.resultlaboservice.service.ResultatLaboService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resultats")
@RequiredArgsConstructor
@Slf4j
public class ResultatLaboController {

    private final ResultatLaboService resultatLaboService;
    private final JwtService jwtService;

    // ─── Saisir résultat ───────────────────────────────────────

    @PostMapping("/demandes/{demandeId}")
    public ResponseEntity<APIResponse<ResultatLaboResponse>> saisirResultat(
            @PathVariable UUID demandeId,
            @Valid @RequestBody SaisirResultatRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Résultat saisi avec succès.",
                        resultatLaboService.saisirResultat(
                                demandeId, hopitalId, request)));
    }

    // ─── Consulter résultats ───────────────────────────────────

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<APIResponse<List<ResultatLaboResponse>>> getResultatsParPatient(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Résultats du patient.",
                        resultatLaboService.getResultatsParPatient(
                                patientId, hopitalId)));
    }

    @GetMapping("/laborantin/{laborantinId}")
    public ResponseEntity<APIResponse<List<ResultatLaboResponse>>> getResultatsParLaborantin(
            @PathVariable UUID laborantinId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Résultats du laborantin.",
                        resultatLaboService.getResultatsParLaborantin(
                                laborantinId, hopitalId)));
    }

    @GetMapping("/demandes/{demandeId}")
    public ResponseEntity<APIResponse<ResultatLaboResponse>> getResultatParDemande(
            @PathVariable UUID demandeId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Résultat trouvé.",
                        resultatLaboService.getResultatParDemande(
                                demandeId, hopitalId)));
    }

    @GetMapping("/anomalies")
    public ResponseEntity<APIResponse<List<ResultatLaboResponse>>> getResultatsAvecAnomalie(
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Résultats avec anomalie.",
                        resultatLaboService.getResultatsAvecAnomalie(hopitalId)));
    }

    // ─── Helper ────────────────────────────────────────────────

    private UUID extraireHopitalId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtService.extractHopitalId(authHeader.substring(7));
        }
        throw new TokenManquantException("Token manquant.");
    }
}