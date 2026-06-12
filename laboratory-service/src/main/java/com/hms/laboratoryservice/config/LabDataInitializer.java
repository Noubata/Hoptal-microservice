package com.hms.laboratoryservice.config;

import com.hms.laboratoryservice.data.models.ServiceLabo;
import com.hms.laboratoryservice.data.repositories.ServiceLaboRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LabDataInitializer implements ApplicationRunner {

    private final ServiceLaboRepository serviceLaboRepository;

    private static final UUID DEFAULT_HOPITAL_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<String> services = List.of(
                "Hématologie",
                "Biochimie",
                "Bactériologie",
                "Parasitologie",
                "Sérologie",
                "Urologie"
        );

        for (String nom : services) {
            if (serviceLaboRepository
                    .findByNomAndHopitalId(nom, DEFAULT_HOPITAL_ID)
                    .isEmpty()) {

                ServiceLabo s = new ServiceLabo();
                s.setNom(nom);
                s.setHopitalId(DEFAULT_HOPITAL_ID);
                serviceLaboRepository.save(s);
                log.info("Service labo created: {}", nom);
            }
        }
    }
}