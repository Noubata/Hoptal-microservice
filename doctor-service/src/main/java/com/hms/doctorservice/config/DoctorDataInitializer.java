package com.hms.doctorservice.config;

import com.hms.doctorservice.data.models.Departement;
import com.hms.doctorservice.data.models.Specialite;
import com.hms.doctorservice.data.repositories.DepartementRepository;
import com.hms.doctorservice.data.repositories.SpecialiteRepository;
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
public class DoctorDataInitializer implements ApplicationRunner {

    private final SpecialiteRepository specialiteRepository;
    private final DepartementRepository departementRepository;

    private static final UUID DEFAULT_HOPITAL_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSpecialites();
        createDepartements();
    }

    private void createSpecialites() {
        List<String> specialites = List.of(
                "Médecine Générale",
                "Pédiatrie",
                "Gynécologie",
                "Chirurgie",
                "Cardiologie",
                "Dermatologie",
                "Ophtalmologie",
                "ORL",
                "Neurologie",
                "Radiologie"
        );

        for (String nom : specialites) {
            if (specialiteRepository
                    .findByNomAndHopitalId(nom, DEFAULT_HOPITAL_ID)
                    .isEmpty()) {

                Specialite s = new Specialite();
                s.setNom(nom);
                s.setHopitalId(DEFAULT_HOPITAL_ID);
                specialiteRepository.save(s);
                log.info("Specialite created: {}", nom);
            }
        }
    }

    private void createDepartements() {
        List<String> departements = List.of(
                "Urgences",
                "Maternité",
                "Pédiatrie",
                "Chirurgie",
                "Consultation Externe",
                "Laboratoire",
                "Radiologie",
                "Pharmacie"
        );

        for (String nom : departements) {
            if (departementRepository
                    .findByNomAndHopitalId(nom, DEFAULT_HOPITAL_ID)
                    .isEmpty()) {

                Departement d = new Departement();
                d.setNom(nom);
                d.setHopitalId(DEFAULT_HOPITAL_ID);
                departementRepository.save(d);
                log.info("Departement created: {}", nom);
            }
        }
    }
}