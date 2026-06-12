package com.hms.authservice.config;

import com.hms.authservice.data.models.Role;
import com.hms.authservice.data.models.User;
import com.hms.authservice.data.repositories.RoleRepository;
import com.hms.authservice.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)  // runs after RoleInitializer which is @Order(1)
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // This is the first hospital UUID
    // In production this comes from tenant-service
    // For now hardcode one for testing
    private static final UUID DEFAULT_HOPITAL_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Only create if no admin exists for default hospital
        if (userRepository.existsByNomUtilisateurAndHopitalId(
                "admin", DEFAULT_HOPITAL_ID)) {
            log.info("Admin already exists, skipping.");
            return;
        }

        Role adminRole = roleRepository.findByNom("ADMIN")
                .orElseThrow(() ->
                        new RuntimeException("ADMIN role not found"));

        User admin = new User();
        admin.setNomUtilisateur("admin");
        admin.setEmail("admin@hoptal.com");
        admin.setMotDePasseHashe(
                passwordEncoder.encode("Admin@1234"));
        admin.setRole(adminRole);
        admin.setHopitalId(DEFAULT_HOPITAL_ID);
        admin.setActive(true);
        admin.setQuartier("N'Djamena");
        admin.setNumeroTelephone("60000000");

        userRepository.save(admin);
        log.info("Default admin created: admin / Admin@1234");
    }
}