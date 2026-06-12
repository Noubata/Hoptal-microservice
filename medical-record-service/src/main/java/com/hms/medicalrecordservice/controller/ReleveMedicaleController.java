package com.hms.medicalrecordservice.controller;

import com.hms.common.security.JwtService;
import com.hms.medicalrecordservice.dtos.requests.AjouterPrescriptionRequest;
import com.hms.medicalrecordservice.dtos.requests.CreerReleveMedicaleRequest;
import com.hms.medicalrecordservice.dtos.responses.APIResponse;
import com.hms.medicalrecordservice.dtos.responses.AjouterPrescriptionResponse;
import com.hms.medicalrecordservice.dtos.responses.CreerReleveMedicaleResponse;
import com.hms.medicalrecordservice.exceptions.TokenManquantException;
import com.hms.medicalrecordservice.service.ReleveMedicaleService;
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
@RequestMapping("/api/releves")
@RequiredArgsConstructor
@Slf4j
public class ReleveMedicaleController {

    private final ReleveMedicaleService releveMedicaleService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<APIResponse<CreerReleveMedicaleResponse>> creerReleve(
            @Valid @RequestBody CreerReleveMedicaleRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Relevé médical créé.",
                        releveMedicaleService.creerReleve(
                                request, hopitalId)));
    }

    @PostMapping("/{releveId}/prescriptions")
    public ResponseEntity<APIResponse<AjouterPrescriptionResponse>> ajouterPrescription(
            @PathVariable UUID releveId,
            @Valid @RequestBody AjouterPrescriptionRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        "Prescription ajoutée.",
                        releveMedicaleService.ajouterPrescription(
                                releveId, hopitalId, request)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<APIResponse<List<CreerReleveMedicaleResponse>>> getHistoriquePatient(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Historique du patient.",
                        releveMedicaleService.getHistoriquePatient(
                                patientId, hopitalId)));
    }

    @GetMapping("/docteur/{docteurId}")
    public ResponseEntity<APIResponse<List<CreerReleveMedicaleResponse>>> getRelevesDocteur(
            @PathVariable UUID docteurId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Relevés du médecin.",
                        releveMedicaleService.getRelevesDocteur(
                                docteurId, hopitalId)));
    }

    @GetMapping("/{releveId}")
    public ResponseEntity<APIResponse<CreerReleveMedicaleResponse>> getReleveById(
            @PathVariable UUID releveId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Relevé trouvé.",
                        releveMedicaleService.getReleveById(
                                releveId, hopitalId)));
    }

    @GetMapping("/{releveId}/prescriptions")
    public ResponseEntity<APIResponse<List<AjouterPrescriptionResponse>>> getPrescriptionsReleve(
            @PathVariable UUID releveId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Prescriptions du relevé.",
                        releveMedicaleService.getPrescriptionsReleve(
                                releveId, hopitalId)));
    }

    private UUID extraireHopitalId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtService.extractHopitalId(authHeader.substring(7));
        }
        throw new TokenManquantException("Token manquant.");
    }
}
