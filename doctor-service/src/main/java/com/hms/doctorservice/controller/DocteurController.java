package com.hms.doctorservice.controller;

import com.hms.common.security.JwtService;
import com.hms.doctorservice.dtos.requests.CreerDocteurRequest;
import com.hms.doctorservice.dtos.requests.UpdateDoctorRequest;
import com.hms.doctorservice.dtos.responses.APIResponse;
import com.hms.doctorservice.dtos.responses.CreerDocteurResponse;
import com.hms.doctorservice.exceptions.TokenManquantException;
import com.hms.doctorservice.service.DocteurService;
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
@RequestMapping("/api/docteurs")
@RequiredArgsConstructor
@Slf4j
public class DocteurController {

    private final DocteurService docteurService;
    private final JwtService jwtService;

    @PostMapping("/creer-docteur")
    public ResponseEntity<APIResponse<CreerDocteurResponse>> creerDoctor(
            @Valid @RequestBody CreerDocteurRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));

        CreerDocteurResponse response =
                docteurService.creerDoctor(request, hopitalId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>("Médecin créé avec succès.", response));
    }

    @PutMapping("/{docteurId}")
    public ResponseEntity<APIResponse<CreerDocteurResponse>> updateDoctor(
            @PathVariable UUID docteurId,
            @Valid @RequestBody UpdateDoctorRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));

        CreerDocteurResponse response =
                docteurService.updateDoctor(docteurId, hopitalId, request);

        return ResponseEntity.ok(
                new APIResponse<>("Médecin mis à jour.", response));
    }

    @GetMapping("/rechercher-docteur")
    public ResponseEntity<APIResponse<List<CreerDocteurResponse>>> rechercherDoctors(
            HttpServletRequest httpRequest) {

        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));

        List<CreerDocteurResponse> response =
                docteurService.rechercherDoctors(hopitalId);

        return ResponseEntity.ok(
                new APIResponse<>("Liste des médecins.", response));
    }

    @GetMapping("/{docteurId}")
    public ResponseEntity<APIResponse<CreerDocteurResponse>> getDoctorById(
            @PathVariable UUID docteurId,
            HttpServletRequest httpRequest) {

        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));

        CreerDocteurResponse response =
                docteurService.getDoctorById(docteurId, hopitalId);

        return ResponseEntity.ok(
                new APIResponse<>("Médecin trouvé.", response));
    }

    private String extraireToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new TokenManquantException("Token manquant.");
    }
}