package com.hms.laboratoryservice.controller;

import com.hms.common.security.JwtService;
import com.hms.laboratoryservice.dtos.requests.CreerDemandeAnalyseRequest;
import com.hms.laboratoryservice.dtos.requests.CreerLaborantinRequest;
import com.hms.laboratoryservice.dtos.requests.CreerServiceLaboRequest;
import com.hms.laboratoryservice.dtos.responses.APIResponse;
import com.hms.laboratoryservice.dtos.responses.CreerLaborantinResponse;
import com.hms.laboratoryservice.dtos.responses.DemandeAnalyseResponse;
import com.hms.laboratoryservice.dtos.responses.ServiceLaboResponse;
import com.hms.laboratoryservice.exceptions.TokenManquantException;
import com.hms.laboratoryservice.service.LaborantinService;
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
@RequestMapping("/api/laboratoire")
@RequiredArgsConstructor
@Slf4j
public class LaborantinController {

    private final LaborantinService laboratoryService;
    private final JwtService jwtService;

    // ─── Laborantins ───────────────────────────────────────────

    @PostMapping("/laborantins")
    public ResponseEntity<APIResponse<CreerLaborantinResponse>> creerLaborantin(
            @Valid @RequestBody CreerLaborantinRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Laborantin créé avec succès.",
                        laboratoryService.creerLaborantin(request, hopitalId)));
    }

    @GetMapping("/laborantins")
    public ResponseEntity<APIResponse<List<CreerLaborantinResponse>>> getAllLaborantins(
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Liste des laborantins.",
                        laboratoryService.getAllLaborantins(hopitalId)));
    }

    // ─── Services Labo ─────────────────────────────────────────

    @PostMapping("/services")
    public ResponseEntity<APIResponse<ServiceLaboResponse>> creerService(
            @Valid @RequestBody CreerServiceLaboRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Service créé avec succès.",
                        laboratoryService.creerService(request, hopitalId)));
    }

    @GetMapping("/services")
    public ResponseEntity<APIResponse<List<ServiceLaboResponse>>> getAllServices(
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Liste des services.",
                        laboratoryService.getAllServices(hopitalId)));
    }

    // ─── Demandes Analyse ──────────────────────────────────────

    @PostMapping("/demandes")
    public ResponseEntity<APIResponse<DemandeAnalyseResponse>> creerDemande(
            @Valid @RequestBody CreerDemandeAnalyseRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Demande d'analyse créée.",
                        laboratoryService.creerDemandeAnalyse(request, hopitalId)));
    }

    @GetMapping("/demandes/laborantin/{laborantinId}/attente")
    public ResponseEntity<APIResponse<List<DemandeAnalyseResponse>>> getDemandesEnAttente(
            @PathVariable UUID laborantinId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Demandes en attente.",
                        laboratoryService.getDemandesEnAttente(
                                laborantinId, hopitalId)));
    }

    @GetMapping("/demandes/patient/{patientId}")
    public ResponseEntity<APIResponse<List<DemandeAnalyseResponse>>> getDemandesParPatient(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Demandes du patient.",
                        laboratoryService.getDemandesParPatient(
                                patientId, hopitalId)));
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