package com.hms.resultlaboservice.service;

import com.hms.resultlaboservice.data.models.ResultatLabo;
import com.hms.resultlaboservice.data.models.StatutResultat;
import com.hms.resultlaboservice.data.repositories.ResultatLaboRepository;
import com.hms.resultlaboservice.dtos.requests.SaisirResultatRequest;
import com.hms.resultlaboservice.dtos.responses.ResultatLaboResponse;
import com.hms.resultlaboservice.exceptions.ResultatDejaSaisiException;
import com.hms.resultlaboservice.exceptions.ResultatIntrouvableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultatLaboServiceImpl implements ResultatLaboService {

    private final ResultatLaboRepository resultatLaboRepository;

    @Transactional
    @Override
    public ResultatLaboResponse saisirResultat(
            UUID demandeId,
            UUID hopitalId,
            SaisirResultatRequest request) {

        // Check if result already exists for this demande
        if (resultatLaboRepository
                .existsByDemandeIdAndHopitalId(demandeId, hopitalId)) {
            throw new ResultatDejaSaisiException(
                    "Ce résultat a déjà été saisi.");
        }

        ResultatLabo resultat = new ResultatLabo();
        resultat.setHopitalId(hopitalId);
        resultat.setDemandeId(demandeId);
        resultat.setPatientId(request.patientId());
        resultat.setLaborantinId(request.laborantinId());
        resultat.setTypeAnalyse(request.typeAnalyse());
        resultat.setResultat(request.resultat());
        resultat.setValeurNormale(request.valeurNormale());
        resultat.setUnite(request.unite());
        resultat.setCommentaire(request.commentaire());
        resultat.setDateDeTest(LocalDateTime.now());
        resultat.setAnomalie(
                detecterAnomalie(request.resultat(), request.valeurNormale()));
        resultat.setStatutResultat(StatutResultat.DISPONIBLE);

        ResultatLabo saved = resultatLaboRepository.save(resultat);

        log.info("Résultat saisi pour demande: {} anomalie: {}",
                demandeId, saved.isAnomalie());

        return toResponse(saved);
    }

    @Override
    public List<ResultatLaboResponse> getResultatsParPatient(
            UUID patientId,
            UUID hopitalId) {

        return resultatLaboRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ResultatLaboResponse> getResultatsParLaborantin(
            UUID laborantinId,
            UUID hopitalId) {

        return resultatLaboRepository
                .findByLaborantinIdAndHopitalId(laborantinId, hopitalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ResultatLaboResponse getResultatParDemande(
            UUID demandeId,
            UUID hopitalId) {

        return resultatLaboRepository
                .findByDemandeIdAndHopitalId(demandeId, hopitalId)
                .map(this::toResponse)
                .orElseThrow(() ->
                        new ResultatIntrouvableException(
                                "Résultat introuvable."));
    }

    @Override
    public List<ResultatLaboResponse> getResultatsAvecAnomalie(
            UUID hopitalId) {

        return resultatLaboRepository
                .findByHopitalIdAndAnomalieTrue(hopitalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private boolean detecterAnomalie(
            String resultat, String valeurNormale) {
        try {
            double valeur = Double.parseDouble(resultat.trim());
            if (valeurNormale.contains("-")) {
                String[] parts = valeurNormale.split("-");
                double min = Double.parseDouble(parts[0].trim());
                double max = Double.parseDouble(parts[1].trim());
                return valeur < min || valeur > max;
            }
            if (valeurNormale.startsWith("<")) {
                double max = Double.parseDouble(
                        valeurNormale.substring(1).trim());
                return valeur >= max;
            }
            if (valeurNormale.startsWith(">")) {
                double min = Double.parseDouble(
                        valeurNormale.substring(1).trim());
                return valeur <= min;
            }
            return false;
        } catch (NumberFormatException e) {
            log.warn("Impossible détecter anomalie: {}", resultat);
            return false;
        }
    }

    private ResultatLaboResponse toResponse(ResultatLabo r) {
        return new ResultatLaboResponse(
                r.getResultatId(),
                r.getDemandeId(),
                r.getPatientId(),
                r.getLaborantinId(),
                r.getTypeAnalyse(),
                r.getResultat(),
                r.getValeurNormale(),
                r.getUnite(),
                r.getCommentaire(),
                r.isAnomalie(),
                r.getStatutResultat().name(),
                r.getDateDeTest(),
                r.getHopitalId()
        );
    }
}