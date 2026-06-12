package com.hms.authservice.service;

import com.hms.common.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import com.hms.authservice.data.models.Role;
import com.hms.authservice.data.models.User;
import com.hms.authservice.data.repositories.RoleRepository;
import com.hms.authservice.data.repositories.UserRepository;
import com.hms.authservice.dtos.requests.ActiverDesactiverUserRequest;
import com.hms.authservice.dtos.requests.ChangerMotDePasseRequest;
import com.hms.authservice.dtos.requests.EnregistrerUtilisateurRequest;
import com.hms.authservice.dtos.requests.LoginRequest;
import com.hms.authservice.dtos.responses.ActiverDesactiverUserResponse;
import com.hms.authservice.dtos.responses.ChangerMotDePasseResponse;
import com.hms.authservice.dtos.responses.EnregisterUtilisateurResponse;
import com.hms.authservice.dtos.responses.LoginResponse;
import com.hms.authservice.exceptions.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Transactional
    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository
                .findByNomUtilisateurAndHopitalId(
                        request.nomUtilisateur(),
                        request.hopitalId())
                .orElseThrow(() ->
                        new UtilisateurIntrouvableException("Introuvable."));

        if (!user.isActive()) {
            throw new CompteDesactiveException("Compte désactivé.");
        }

        if (!passwordEncoder.matches(
                request.motDePasse(), user.getMotDePasseHashe())) {
            throw new MotDePasseIncorrectException("Mot de passe incorrect.");
        }

        String token = generateToken(user);

        return new LoginResponse(
                token,
                user.getRole().getNom(),
                user.getNomUtilisateur(),
                user.getUserId(),
                user.getHopitalId()
        );
    }

    // Token generation stays private inside auth-service
    private String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getNomUtilisateur())
                .claim("userId", user.getUserId().toString())
                .claim("hopitalId", user.getHopitalId().toString())
                .claim("role", user.getRole().getNom())
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    @Transactional
    @Override
    public EnregisterUtilisateurResponse enregistrerUtilisateur(
            EnregistrerUtilisateurRequest request,
            UUID hopitalId) {
        if (userRepository.existsByNomUtilisateurAndHopitalId(
                request.nomUtilisateur(), hopitalId)) {
            throw new UtilisateurExisteDejaException(
                    "Le nom d'utilisateur '"
                            + request.nomUtilisateur()
                            + "' est déjà utilisé dans cet hôpital.");
        }
        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() ->
                        new RoleIntrouvableException("Rôle introuvable."));

        User user = new User();
        user.setNomUtilisateur(request.nomUtilisateur());
        user.setEmail(request.email());
        user.setMotDePasseHashe(passwordEncoder.encode(request.motDePasse()));
        user.setNumeroTelephone(request.numeroTelephone());
        user.setQuartier(request.quartier());
        user.setRole(role);
        user.setHopitalId(hopitalId);
        user.setActive(true);

        User saved = userRepository.save(user);

        log.info("Nouvel utilisateur enregistré: {} pour l'hôpital: {}",
                saved.getNomUtilisateur(), hopitalId);

        return new EnregisterUtilisateurResponse(
                saved.getUserId(),
                saved.getNomUtilisateur(),
                saved.getEmail(),
                saved.getRole().getNom(),
                saved.getHopitalId(),
                saved.getDateCreation()
        );
    }
    @Transactional
    @Override
    public ChangerMotDePasseResponse changerMotDePasse(
            UUID userId,
            UUID hopitalId,
            ChangerMotDePasseRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UtilisateurIntrouvableException("Utilisateur introuvable."));
        if (!user.getHopitalId().equals(hopitalId)) {
            throw new AccesRefuseException("Accès refusé.");
        }
        if (!passwordEncoder.matches(
                request.ancienMotDePasse(),
                user.getMotDePasseHashe())) {
            throw new AncienMotDePasseIncorrectException(
                    "Ancien mot de passe incorrect.");
        }

        user.setMotDePasseHashe(
                passwordEncoder.encode(request.nouveauMotDePasse()));
        userRepository.save(user);

        log.info("Mot de passe modifié pour l'utilisateur: {}", userId);

        return new ChangerMotDePasseResponse("Mot de passe modifié avec succès.");
    }

    @Transactional
    @Override
    public ActiverDesactiverUserResponse activerDesactiverUser(
            UUID userId,
            UUID hopitalId,
            ActiverDesactiverUserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UtilisateurIntrouvableException("Utilisateur introuvable."));
        if (!user.getHopitalId().equals(hopitalId)) {
            throw new AccesRefuseException("Accès refusé.");
        }
        user.setActive(request.active());
        userRepository.save(user);
        log.info("Utilisateur {} {} par hôpital: {}",
                userId,
                request.active() ? "activé" : "désactivé",
                hopitalId);
        return new ActiverDesactiverUserResponse(
                user.getUserId(),
                user.isActive(),
                user.getNomUtilisateur(),
                user.getRole().getNom(),
                LocalDateTime.now()
        );
    }
}
