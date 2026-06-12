package com.hms.prescriptionservice.controller;

import com.hms.common.security.JwtService;
import com.hms.prescriptionservice.dtos.requests.VerifierAllergieRequest;
import com.hms.prescriptionservice.dtos.responses.APIResponse;
import com.hms.prescriptionservice.dtos.responses.PrescriptionActiveResponse;
import com.hms.prescriptionservice.dtos.responses.VerifierAllergieResponse;
import com.hms.prescriptionservice.exceptions.TokenManquantException;
import com.hms.prescriptionservice.service.PrescriptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@Slf4j
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final JwtService jwtService;

    @GetMapping("/patient/{patientId}/actives")
    public ResponseEntity<APIResponse<List<PrescriptionActiveResponse>>> getPrescriptionsActives(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Prescriptions actives.",
                        prescriptionService.getPrescriptionsActives(
                                patientId, hopitalId)));
    }

    @PostMapping("/verifier-allergies")
    public ResponseEntity<APIResponse<VerifierAllergieResponse>> verifierAllergies(
            @Valid @RequestBody VerifierAllergieRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>(
                        "Vérification effectuée.",
                        prescriptionService.verifierAllergies(
                                request, hopitalId)));
    }

    private UUID extraireHopitalId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtService.extractHopitalId(authHeader.substring(7));
        }
        throw new TokenManquantException("Token manquant.");
    }
}
