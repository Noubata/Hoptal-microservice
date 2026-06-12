package com.hms.laboratoryservice.service;

import com.hms.laboratoryservice.data.models.DemandeAnalyse;
import com.hms.laboratoryservice.data.models.Laborantin;
import com.hms.laboratoryservice.data.models.ServiceLabo;
import com.hms.laboratoryservice.data.models.StatutDemande;
import com.hms.laboratoryservice.data.repositories.DemandeAnalyseRepository;
import com.hms.laboratoryservice.data.repositories.LaborantinRepository;
import com.hms.laboratoryservice.data.repositories.ServiceLaboRepository;
import com.hms.laboratoryservice.dtos.requests.CreerDemandeAnalyseRequest;
import com.hms.laboratoryservice.dtos.requests.CreerLaborantinRequest;
import com.hms.laboratoryservice.dtos.requests.CreerServiceLaboRequest;
import com.hms.laboratoryservice.dtos.responses.CreerLaborantinResponse;
import com.hms.laboratoryservice.dtos.responses.DemandeAnalyseResponse;
import com.hms.laboratoryservice.dtos.responses.ServiceLaboResponse;
import com.hms.laboratoryservice.exceptions.LaborantinExisteDejaException;
import com.hms.laboratoryservice.exceptions.LaborantinIntrouvableException;
import com.hms.laboratoryservice.exceptions.ServiceIntrouvableException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaborantinServiceImpl implements LaborantinService {

    private final LaborantinRepository laborantinRepository;
    private final ServiceLaboRepository serviceLaboRepository;
    private final DemandeAnalyseRepository demandeAnalyseRepository;

    @Transactional
    @Override
    public CreerLaborantinResponse creerLaborantin(
            CreerLaborantinRequest request,
            UUID hopitalId) {

        if (laborantinRepository.existsByUserIdAndHopitalId(
                request.userId(), hopitalId)) {
            throw new LaborantinExisteDejaException(
                    "Ce laborantin existe déjà dans cet hôpital.");
        }

        ServiceLabo service = serviceLaboRepository
                .findByServiceIdAndHopitalId(
                        request.serviceId(), hopitalId)
                .orElseThrow(() ->
                        new ServiceIntrouvableException("Service introuvable."));

        Laborantin laborantin = new Laborantin();
        laborantin.setHopitalId(hopitalId);
        laborantin.setUserId(request.userId());
        laborantin.setNom(request.nom());
        laborantin.setPrenom(request.prenom());
        laborantin.setEmail(request.email());
        laborantin.setNumeroDeTelephone(request.numeroDeTelephone());
        laborantin.setService(service);

        Laborantin saved = laborantinRepository.save(laborantin);

        log.info("Laborantin créé: {} hôpital: {}",
                saved.getLaborantinId(), hopitalId);

        return toLaborantinResponse(saved);
    }

    @Transactional
    @Override
    public DemandeAnalyseResponse creerDemandeAnalyse(
            CreerDemandeAnalyseRequest request,
            UUID hopitalId) {

        laborantinRepository
                .findByLaborantinIdAndHopitalId(
                        request.laborantinId(), hopitalId)
                .orElseThrow(() ->
                        new LaborantinIntrouvableException(
                                "Laborantin introuvable."));

        DemandeAnalyse demande = new DemandeAnalyse();
        demande.setHopitalId(hopitalId);
        demande.setPatientId(request.patientId());
        demande.setLaborantinId(request.laborantinId());
        demande.setTypeAnalyse(request.typeAnalyse());
        demande.setStatut(StatutDemande.EN_ATTENTE);

        DemandeAnalyse saved = demandeAnalyseRepository.save(demande);

        log.info("Demande analyse créée: {} patient: {}",
                saved.getDemandeId(), request.patientId());

        return toDemandeResponse(saved);
    }

    @Override
    public List<DemandeAnalyseResponse> getDemandesEnAttente(
            UUID laborantinId,
            UUID hopitalId) {

        laborantinRepository
                .findByLaborantinIdAndHopitalId(laborantinId, hopitalId)
                .orElseThrow(() ->
                        new LaborantinIntrouvableException(
                                "Laborantin introuvable."));

        return demandeAnalyseRepository
                .findByLaborantinIdAndHopitalIdAndStatut(
                        laborantinId, hopitalId, StatutDemande.EN_ATTENTE)
                .stream()
                .map(this::toDemandeResponse)
                .toList();
    }

    @Override
    public List<DemandeAnalyseResponse> getDemandesParPatient(
            UUID patientId,
            UUID hopitalId) {

        return demandeAnalyseRepository
                .findByPatientIdAndHopitalId(patientId, hopitalId)
                .stream()
                .map(this::toDemandeResponse)
                .toList();
    }

    @Override
    public List<CreerLaborantinResponse> getAllLaborantins(UUID hopitalId) {
        return laborantinRepository.findByHopitalId(hopitalId)
                .stream()
                .map(this::toLaborantinResponse)
                .toList();
    }

    @Override
    public List<ServiceLaboResponse> getAllServices(UUID hopitalId) {
        return serviceLaboRepository.findByHopitalId(hopitalId)
                .stream()
                .map(this::toServiceResponse)
                .toList();
    }

    @Transactional
    @Override
    public ServiceLaboResponse creerService(
            CreerServiceLaboRequest request,
            UUID hopitalId) {

        ServiceLabo service = new ServiceLabo();
        service.setHopitalId(hopitalId);
        service.setNom(request.nom());
        service.setDescription(request.description());

        ServiceLabo saved = serviceLaboRepository.save(service);

        log.info("Service labo créé: {} hôpital: {}",
                saved.getServiceId(), hopitalId);

        return toServiceResponse(saved);
    }

    private CreerLaborantinResponse toLaborantinResponse(Laborantin l) {
        return new CreerLaborantinResponse(
                l.getLaborantinId(),
                l.getNom(),
                l.getPrenom(),
                l.getEmail(),
                l.getService().getNom(),
                l.getHopitalId()
        );
    }

    private DemandeAnalyseResponse toDemandeResponse(DemandeAnalyse d) {
        return new DemandeAnalyseResponse(
                d.getDemandeId(),
                d.getPatientId(),
                d.getLaborantinId(),
                d.getTypeAnalyse(),
                d.getStatut().name(),
                d.getHopitalId(),
                d.getDateCreation()
        );
    }

    private ServiceLaboResponse toServiceResponse(ServiceLabo s) {
        return new ServiceLaboResponse(
                s.getServiceId(),
                s.getNom(),
                s.getDescription(),
                s.getHopitalId()
        );
    }
}