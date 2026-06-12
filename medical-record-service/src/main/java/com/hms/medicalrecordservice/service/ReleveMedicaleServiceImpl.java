package com.hms.medicalrecordservice.service;

import com.hms.medicalrecordservice.data.models.Prescription;
import com.hms.medicalrecordservice.data.models.ReleveMedicale;
import com.hms.medicalrecordservice.data.models.StatutPrescription;
import com.hms.medicalrecordservice.data.repositories.PrescriptionRepository;
import com.hms.medicalrecordservice.data.repositories.ReleveMedicaleRepository;
import com.hms.medicalrecordservice.dtos.requests.AjouterPrescriptionRequest;
import com.hms.medicalrecordservice.dtos.requests.CreerReleveMedicaleRequest;
import com.hms.medicalrecordservice.dtos.responses.AjouterPrescriptionResponse;
import com.hms.medicalrecordservice.dtos.responses.CreerReleveMedicaleResponse;
import com.hms.medicalrecordservice.exceptions.ReleveIntrouvableException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleveMedicaleServiceImpl implements ReleveMedicaleService {

    private final ReleveMedicaleRepository releveMedicaleRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Transactional
    @Override
    public CreerReleveMedicaleResponse creerReleve(
            CreerReleveMedicaleRequest request,
            UUID hopitalId) {

        ReleveMedicale releve = new ReleveMedicale();
        releve.setHopitalId(hopitalId);
        releve.setPatientId(request.patientId());
        releve.setDocteurId(request.docteurId());
        releve.setDiagnostic(request.diagnostic());
        releve.setSymptomes(request.symptomes());
        releve.setNotes(request.notes());
        releve.setTypeVisite(request.typeVisite());
        releve.setDureeConsultation(request.dureeConsultation());
        releve.setDateDeVisite(LocalDateTime.now());

        ReleveMedicale saved = releveMedicaleRepository.save(releve);

        log.info("Relevé médical créé: {} patient: {} hôpital: {}",
                saved.getReleveId(), request.patientId(), hopitalId);

        return toReleveResponse(saved);
    }

    @Transactional
    @Override
    public AjouterPrescriptionResponse ajouterPrescription(
            UUID releveId,
            UUID hopitalId,
            AjouterPrescriptionRequest request) {

        ReleveMedicale releve = releveMedicaleRepository
                .findByReleveIdAndHopitalId(releveId, hopitalId)
                .orElseThrow(() ->
                        new ReleveIntrouvableException(
                                "Relevé médical introuvable."));

        Prescription prescription = new Prescription();
        prescription.setHopitalId(hopitalId);
        prescription.setPatientId(releve.getPatientId());
        prescription.setReleveMedicale(releve);
        prescription.setNomDuMedicament(request.nomDuMedicament());
        prescription.setDosage(request.dosage());
        prescription.setFrequence(request.frequence());
        prescription.setDuree(request.duree());
        prescription.setInstructions(request.instructions());
        prescription.setRenouvellable(request.renouvellable());
        prescription.setDateDePrescription(LocalDateTime.now());
        prescription.setStatut(StatutPrescription.ACTIVE);

        Prescription saved = prescriptionRepository.save(prescription);

        log.info("Prescription ajoutée: {} pour patient: {}",
                saved.getPrescriptionId(), releve.getPatientId());

        return toPrescriptionResponse(saved);
    }

    @Override
    public List<CreerReleveMedicaleResponse> getHistoriquePatient(
            UUID patientId,
            UUID hopitalId) {

        return releveMedicaleRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .stream()
                .map(this::toReleveResponse)
                .toList();
    }

    @Override
    public List<CreerReleveMedicaleResponse> getRelevesDocteur(
            UUID docteurId,
            UUID hopitalId) {

        return releveMedicaleRepository
                .findByDocteurIdAndHopitalId(docteurId, hopitalId)
                .stream()
                .map(this::toReleveResponse)
                .toList();
    }

    @Override
    public CreerReleveMedicaleResponse getReleveById(
            UUID releveId,
            UUID hopitalId) {

        ReleveMedicale releve = releveMedicaleRepository
                .findByReleveIdAndHopitalId(releveId, hopitalId)
                .orElseThrow(() ->
                        new ReleveIntrouvableException(
                                "Relevé médical introuvable."));

        log.info("Récupération relevé: {} hôpital: {}",
                releveId, hopitalId);

        return toReleveResponse(releve);
    }

    @Override
    public List<AjouterPrescriptionResponse> getPrescriptionsReleve(
            UUID releveId,
            UUID hopitalId) {

        releveMedicaleRepository
                .findByReleveIdAndHopitalId(releveId, hopitalId)
                .orElseThrow(() ->
                        new ReleveIntrouvableException(
                                "Relevé médical introuvable."));

        return prescriptionRepository
                .findByReleveMedicaleReleveIdAndHopitalId(
                        releveId, hopitalId)
                .stream()
                .map(this::toPrescriptionResponse)
                .toList();
    }

    // Private helpers

    private CreerReleveMedicaleResponse toReleveResponse(ReleveMedicale r) {
        return new CreerReleveMedicaleResponse(
                r.getReleveId(),
                r.getPatientId(),
                r.getDocteurId(),
                r.getDiagnostic(),
                r.getSymptomes(),
                r.getNotes(),
                r.getTypeVisite(),
                r.getDureeConsultation(),
                r.getDateDeVisite(),
                r.getHopitalId()
        );
    }

    private AjouterPrescriptionResponse toPrescriptionResponse(
            Prescription p) {
        return new AjouterPrescriptionResponse(
                p.getPrescriptionId(),
                p.getPatientId(),
                p.getReleveMedicale().getReleveId(),
                p.getNomDuMedicament(),
                p.getDosage(),
                p.getFrequence(),
                p.getDuree(),
                p.getInstructions(),
                p.isRenouvellable(),
                p.getStatut().name(),
                p.getDateDePrescription(),
                p.getHopitalId()
        );
    }
}
