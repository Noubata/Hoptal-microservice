package com.hms.authservice.config;

import com.hms.authservice.data.models.Role;
import com.hms.authservice.data.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> roles = List.of("SUPER_ADMIN", "ADMIN", "DOCTOR", "NURSE", "RECEPTIONIST", "LABORANTIN", "PATIENT");

        for (String roleName : roles) {
            if (roleRepository.findByNom(roleName).isEmpty()) {
                Role role = new Role();
                role.setNom(roleName);
                roleRepository.save(role);
                log.info("Role created: {}", roleName);
            }
        }
    }
}
