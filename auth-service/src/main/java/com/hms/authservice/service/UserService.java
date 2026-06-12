package com.hms.authservice.service;

import com.hms.authservice.data.models.User;
import com.hms.authservice.dtos.requests.ActiverDesactiverUserRequest;
import com.hms.authservice.dtos.requests.ChangerMotDePasseRequest;
import com.hms.authservice.dtos.requests.EnregistrerUtilisateurRequest;
import com.hms.authservice.dtos.requests.LoginRequest;
import com.hms.authservice.dtos.responses.ActiverDesactiverUserResponse;
import com.hms.authservice.dtos.responses.ChangerMotDePasseResponse;
import com.hms.authservice.dtos.responses.EnregisterUtilisateurResponse;
import com.hms.authservice.dtos.responses.LoginResponse;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface UserService {

    @Transactional
    LoginResponse login(LoginRequest loginRequest);

    @Transactional
    EnregisterUtilisateurResponse enregistrerUtilisateur(
            EnregistrerUtilisateurRequest request,
            UUID hopitalId);

    @Transactional
    ChangerMotDePasseResponse changerMotDePasse(
            UUID userId,
            UUID hopitalId,
            ChangerMotDePasseRequest request);

    @Transactional
    ActiverDesactiverUserResponse activerDesactiverUser(
            UUID userId,
            UUID hopitalId,
            ActiverDesactiverUserRequest request);
}
