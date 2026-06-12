package com.hms.patientservice.controller;


import com.hms.common.security.JwtService;
import com.hms.patientservice.dtos.requests.AjouterAllergieRequest;
import com.hms.patientservice.dtos.requests.AjouterAntecedentRequest;
import com.hms.patientservice.dtos.requests.CreerPatientRequest;
import com.hms.patientservice.dtos.responses.*;
import com.hms.patientservice.exceptions.TokenManquantException;
import com.hms.patientservice.service.PatientService;
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
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;
    private final JwtService jwtService;

    @PostMapping("/creer-patient")
    public ResponseEntity<APIResponse<CreerPatientResponse>> creerPatient(
            @Valid @RequestBody CreerPatientRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>("Patient créé avec succès.",
                        patientService.creerPatient(request, hopitalId)));
    }

    @GetMapping("/{patientId}/dossier")
    public ResponseEntity<APIResponse<DossierCompletResponse>> getDossierComplet(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Dossier complet.",
                        patientService.getDossierComplet(patientId, hopitalId)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CreerPatientResponse>>> rechercherPatients(
            @RequestParam(required = false) String recherche,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Liste des patients.",
                        patientService.rechercherPatients(hopitalId, recherche)));
    }

    @GetMapping("/recents")
    public ResponseEntity<APIResponse<List<CreerPatientResponse>>> getPatientsRecents(
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Patients récents.",
                        patientService.getPatientsRecents(hopitalId)));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<APIResponse<CreerPatientResponse>> getPatientById(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Patient trouvé.",
                        patientService.getPatientById(patientId, hopitalId)));
    }

    @GetMapping("/dossier/{numeroDossier}")
    public ResponseEntity<APIResponse<CreerPatientResponse>> getPatientByDossier(
            @PathVariable String numeroDossier,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Patient trouvé.",
                        patientService.getPatientByDossier(
                                numeroDossier, hopitalId)));
    }

    @PostMapping("/{patientId}/allergies")
    public ResponseEntity<APIResponse<AjouterAllergieResponse>> ajouterAllergie(
            @PathVariable UUID patientId,
            @Valid @RequestBody AjouterAllergieRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>("Allergie ajoutée.",
                        patientService.ajouterAllergie(
                                patientId, hopitalId, request)));
    }

    @PostMapping("/{patientId}/antecedents")
    public ResponseEntity<APIResponse<AjouterAntecedentResponse>> ajouterAntecedent(
            @PathVariable UUID patientId,
            @Valid @RequestBody AjouterAntecedentRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>("Antécédent ajouté.",
                        patientService.ajouterAntecedent(
                                patientId, hopitalId, request)));
    }

    @GetMapping("/{patientId}/allergies")
    public ResponseEntity<APIResponse<List<AjouterAllergieResponse>>> getAllergies(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Allergies du patient.",
                        patientService.getAllergies(patientId, hopitalId)));
    }

    @GetMapping("/{patientId}/antecedents")
    public ResponseEntity<APIResponse<List<AjouterAntecedentResponse>>> getAntecedents(
            @PathVariable UUID patientId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = extraireHopitalId(httpRequest);
        return ResponseEntity.ok(
                new APIResponse<>("Antécédents du patient.",
                        patientService.getAntecedents(patientId, hopitalId)));
    }

    private UUID extraireHopitalId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtService.extractHopitalId(authHeader.substring(7));
        }
        throw new TokenManquantException("Token manquant.");
    }
}
