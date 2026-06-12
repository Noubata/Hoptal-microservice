package com.hms.authservice.controller;

import com.hms.authservice.dtos.requests.ActiverDesactiverUserRequest;
import com.hms.authservice.dtos.requests.ChangerMotDePasseRequest;
import com.hms.authservice.dtos.requests.EnregistrerUtilisateurRequest;
import com.hms.authservice.dtos.requests.LoginRequest;
import com.hms.authservice.dtos.responses.*;
import com.hms.authservice.exceptions.TokenManquantException;
import com.hms.common.security.JwtService;
import com.hms.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/enregistrer")
    public ResponseEntity<APIResponse<EnregisterUtilisateurResponse>> enregistrerUtilisateur(
            @Valid @RequestBody EnregistrerUtilisateurRequest request,
            HttpServletRequest httpRequest) {

        String token = extraireToken(httpRequest);
        UUID hopitalId = jwtService.extractHopitalId(token);
        log.info("Demande d'enregistrement pour: {} par hôpital: {}",
                request.nomUtilisateur(), hopitalId);
        EnregisterUtilisateurResponse response =
                userService.enregistrerUtilisateur(request, hopitalId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>("logged in successfully", response));
    }

    private String extraireToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new TokenManquantException("Token d'authentification manquant.");
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        log.info("Tentative de connexion pour: {} hôpital: {}",
                request.nomUtilisateur(), request.hopitalId());

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(
                new APIResponse<>("Connexion réussie.", response));
    }

    @PutMapping("/changer-mot-de-passe/{userId}")
    public ResponseEntity<APIResponse<ChangerMotDePasseResponse>> changerMotDePasse(
            @PathVariable UUID userId,
            @Valid @RequestBody ChangerMotDePasseRequest request,
            HttpServletRequest httpRequest) {

        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));
                ChangerMotDePasseResponse response = userService.changerMotDePasse(userId, hopitalId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("mot de passe change avec succes", response));
    }

    @PutMapping("/activer-desactiver/{userId}")
    public ResponseEntity<APIResponse<ActiverDesactiverUserResponse>> activerDesactiverUser(
            @PathVariable UUID userId,
            @Valid @RequestBody ActiverDesactiverUserRequest request,
            HttpServletRequest httpRequest) {
        UUID hopitalId = jwtService.extractHopitalId(
                extraireToken(httpRequest));
        ActiverDesactiverUserResponse response = userService.activerDesactiverUser(userId, hopitalId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>("desactive avec succes", response));
    }
}
