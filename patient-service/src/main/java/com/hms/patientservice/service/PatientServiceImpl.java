package com.hms.patientservice.service;

import com.hms.patientservice.data.models.Allergie;
import com.hms.patientservice.data.models.Antecedent;
import com.hms.patientservice.data.models.Patient;
import com.hms.patientservice.data.models.StatutPatient;
import com.hms.patientservice.data.repositories.AllergieRepository;
import com.hms.patientservice.data.repositories.AntecedentRepository;
import com.hms.patientservice.data.repositories.PatientRepository;
import com.hms.patientservice.dtos.requests.AjouterAllergieRequest;
import com.hms.patientservice.dtos.requests.AjouterAntecedentRequest;
import com.hms.patientservice.dtos.requests.CreerPatientRequest;
import com.hms.patientservice.dtos.responses.AjouterAllergieResponse;
import com.hms.patientservice.dtos.responses.AjouterAntecedentResponse;
import com.hms.patientservice.dtos.responses.CreerPatientResponse;
import com.hms.patientservice.dtos.responses.DossierCompletResponse;
import com.hms.patientservice.exceptions.PatientIntrouvableException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AllergieRepository allergieRepository;
    private final AntecedentRepository antecedentRepository;

    @Transactional
    @Override
    public CreerPatientResponse creerPatient(
            CreerPatientRequest request,
            UUID hopitalId) {

        String numeroDossier = genererNumeroDossier(hopitalId);

        Patient patient = new Patient();
        patient.setHopitalId(hopitalId);
        patient.setNumeroDossier(numeroDossier);
        patient.setNom(request.nom());
        patient.setPrenom(request.prenom());
        patient.setDateDeNaissance(request.dateDeNaissance());
        patient.setSexe(request.sexe());
        patient.setGroupeSanguin(request.groupeSanguin());
        patient.setEmail(request.email());
        patient.setNumeroDeTelephone(request.numeroDeTelephone());
        patient.setAdresse(request.adresse());
        patient.setQuartier(request.quartier());
        patient.setVille(request.ville());
        patient.setNomContactUrgence(request.nomContactUrgence());
        patient.setTelephoneContactUrgence(request.telephoneContactUrgence());
        patient.setEmailContactUrgence(request.emailContactUrgence());
        patient.setStatutPatient(StatutPatient.ACTIF);

        Patient saved = patientRepository.save(patient);

        log.info("Patient créé: {} pour hôpital: {}",
                saved.getNumeroDossier(), hopitalId);

        return toResponse(saved);
    }

    @Override
    public DossierCompletResponse getDossierComplet(
            UUID patientId,
            UUID hopitalId) {

        Patient patient = patientRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        List<AjouterAllergieResponse> allergies = allergieRepository
                .findByPatientPatientId(patientId)
                .stream()
                .map(this::toAllergieResponse)
                .toList();

        List<AjouterAntecedentResponse> antecedents = antecedentRepository
                .findByPatientPatientId(patientId)
                .stream()
                .map(this::toAntecedentResponse)
                .toList();

        log.info("Dossier complet récupéré pour patient: {}", patientId);

        return new DossierCompletResponse(
                toResponse(patient),
                allergies,
                antecedents
        );
    }

    @Override
    public List<CreerPatientResponse> rechercherPatients(
            UUID hopitalId,
            String recherche) {

        if (recherche != null && !recherche.isBlank()) {
            return patientRepository
                    .findByHopitalIdAndNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
                            hopitalId, recherche, recherche)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return patientRepository.findByHopitalId(hopitalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CreerPatientResponse> getPatientsRecents(UUID hopitalId) {
        List<CreerPatientResponse> patients = patientRepository
                .findTop5ByHopitalIdOrderByDateCreationDesc(hopitalId)
                .stream()
                .map(this::toResponse)
                .toList();

        if (patients.isEmpty()) {
            throw new PatientIntrouvableException("Aucun patient trouvé.");
        }

        return patients;
    }

    @Override
    public CreerPatientResponse getPatientById(
            UUID patientId,
            UUID hopitalId) {

        Patient patient = patientRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        log.info("Récupération patient: {} hôpital: {}",
                patientId, hopitalId);

        return toResponse(patient);
    }

    @Override
    public CreerPatientResponse getPatientByDossier(
            String numeroDossier,
            UUID hopitalId) {

        Patient patient = patientRepository
                .findByNumeroDossierAndHopitalId(numeroDossier, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Dossier introuvable."));

        return toResponse(patient);
    }

    @Transactional
    @Override
    public AjouterAllergieResponse ajouterAllergie(
            UUID patientId,
            UUID hopitalId,
            AjouterAllergieRequest request) {

        Patient patient = patientRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        Allergie allergie = new Allergie();
        allergie.setPatient(patient);
        allergie.setSubstance(request.substance());
        allergie.setSeverite(request.severite());
        allergie.setReaction(request.reaction());
        allergie.setDateDecouverte(request.dateDecouverte());

        log.info("Allergie ajoutée pour patient: {}", patientId);

        return toAllergieResponse(allergieRepository.save(allergie));
    }

    @Transactional
    @Override
    public AjouterAntecedentResponse ajouterAntecedent(
            UUID patientId,
            UUID hopitalId,
            AjouterAntecedentRequest request) {

        Patient patient = patientRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        Antecedent antecedent = new Antecedent();
        antecedent.setPatient(patient);
        antecedent.setTypeAntecedent(request.typeAntecedent());
        antecedent.setDescription(request.description());
        antecedent.setDateDebut(request.dateDebut());
        antecedent.setChronique(request.chronique());
        antecedent.setNotes(request.notes());

        log.info("Antécédent ajouté pour patient: {}", patientId);

        return toAntecedentResponse(antecedentRepository.save(antecedent));
    }

    @Override
    public List<AjouterAllergieResponse> getAllergies(
            UUID patientId,
            UUID hopitalId) {

        patientRepository.findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        return allergieRepository.findByPatientPatientId(patientId)
                .stream()
                .map(this::toAllergieResponse)
                .toList();
    }

    @Override
    public List<AjouterAntecedentResponse> getAntecedents(
            UUID patientId,
            UUID hopitalId) {

        patientRepository.findByPatientIdAndHopitalId(patientId, hopitalId)
                .orElseThrow(() ->
                        new PatientIntrouvableException("Patient introuvable."));

        return antecedentRepository.findByPatientPatientId(patientId)
                .stream()
                .map(this::toAntecedentResponse)
                .toList();
    }

    // Private helpers — no mapper class needed

    private String genererNumeroDossier(UUID hopitalId) {
        int annee = Year.now().getValue();
        long count = patientRepository.countByHopitalId(hopitalId) + 1;
        return String.format("DPI-%d-%05d", annee, count);
    }

    private CreerPatientResponse toResponse(Patient p) {
        return new CreerPatientResponse(
                p.getPatientId(),
                p.getNumeroDossier(),
                p.getNom(),
                p.getPrenom(),
                p.getDateDeNaissance(),
                p.getSexe().name(),
                p.getGroupeSanguin().name(),
                p.getStatutPatient().name(),
                p.getNumeroDeTelephone(),
                p.getEmail(),
                p.getHopitalId(),
                p.getDateCreation()
        );
    }

    private AjouterAllergieResponse toAllergieResponse(Allergie a) {
        return new AjouterAllergieResponse(
                a.getAllergieId(),
                a.getSubstance(),
                a.getSeverite(),
                a.getReaction(),
                a.getDateDecouverte()
        );
    }

    private AjouterAntecedentResponse toAntecedentResponse(Antecedent a) {
        return new AjouterAntecedentResponse(
                a.getAntecedentId(),
                a.getTypeAntecedent(),
                a.getDescription(),
                a.getDateDebut(),
                a.isChronique(),
                a.getNotes()
        );
    }
}
