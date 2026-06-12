package com.hms.doctorservice.service;

import com.hms.doctorservice.data.models.Departement;
import com.hms.doctorservice.data.models.Docteur;
import com.hms.doctorservice.data.models.Specialite;
import com.hms.doctorservice.data.repositories.DepartementRepository;
import com.hms.doctorservice.data.repositories.DocteurRepository;
import com.hms.doctorservice.data.repositories.SpecialiteRepository;
import com.hms.doctorservice.dtos.requests.CreerDocteurRequest;
import com.hms.doctorservice.dtos.requests.UpdateDoctorRequest;
import com.hms.doctorservice.dtos.responses.CreerDocteurResponse;
import com.hms.doctorservice.exceptions.DepartementIntrouvableException;
import com.hms.doctorservice.exceptions.MedecinIntrouvableException;
import com.hms.doctorservice.exceptions.NumeroLicenceExisteDejaException;
import com.hms.doctorservice.exceptions.SpecialiteIntrouvableException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DocteurServiceImpl implements DocteurService {

    private final DocteurRepository docteurRepository;
    private final SpecialiteRepository specialiteRepository;
    private final DepartementRepository departementRepository;

    public DocteurServiceImpl(DocteurRepository docteurRepository, SpecialiteRepository specialiteRepository, DepartementRepository departementRepository){
        this.docteurRepository = docteurRepository;
        this.specialiteRepository = specialiteRepository;
        this.departementRepository = departementRepository;
    }

    @Transactional
    @Override
    public CreerDocteurResponse creerDoctor(
            CreerDocteurRequest request,
            UUID hopitalId) {

        if (docteurRepository.existsByNumeroDeLicenceAndHopitalId(
                request.numeroDeLicence(), hopitalId)) {
            throw new NumeroLicenceExisteDejaException(
                    "Un médecin avec le numéro de licence '"
                            + request.numeroDeLicence()
                            + "' existe déjà dans cet hôpital.");
        }

        Specialite specialite = specialiteRepository
                .findById(request.specialiteId())
                .orElseThrow(() ->
                        new SpecialiteIntrouvableException("Spécialité introuvable."));

        Departement departement = departementRepository
                .findById(request.departementId())
                .orElseThrow(() ->
                        new DepartementIntrouvableException("Département introuvable."));

        Docteur doctor = new Docteur();
        doctor.setHopitalId(hopitalId);
        doctor.setUserId(request.userId());
        doctor.setNom(request.nom());
        doctor.setPrenom(request.prenom());
        doctor.setNumeroDeLicence(request.numeroDeLicence());
        doctor.setNumeroDeTelephone(request.numeroDeTelephone());
        doctor.setEmail(request.email());
        doctor.setDateEmbauche(request.dateEmbauche());
        doctor.setSpecialite(specialite);
        doctor.setDepartement(departement);

        Docteur saved = docteurRepository.save(doctor);

        log.info("Médecin créé: {} pour hôpital: {}",
                saved.getDocteurId(), hopitalId);

        return new CreerDocteurResponse(
                saved.getDocteurId(),
                saved.getNom(),
                saved.getPrenom(),
                saved.getNumeroDeLicence(),
                saved.getEmail(),
                saved.getSpecialite().getNom(),
                saved.getDepartement().getNom(),
                saved.getHopitalId(),
                saved.getDateEmbauche()
        );
    }

    @Transactional
    @Override
    public CreerDocteurResponse updateDoctor(
            UUID doctorId,
            UUID hopitalId,
            UpdateDoctorRequest request) {

        Docteur docteur = docteurRepository.findByDocteurIdAndHopitalId(
                        doctorId, hopitalId)
                .orElseThrow(() ->
                        new MedecinIntrouvableException("Médecin introuvable."));

        if (request.nom() != null) docteur.setNom(request.nom());
        if (request.prenom() != null) docteur.setPrenom(request.prenom());
        if (request.numeroDeTelephone() != null)
            docteur.setNumeroDeTelephone(request.numeroDeTelephone());
        if (request.email() != null) docteur.setEmail(request.email());

        if (request.specialiteId() != null) {
            Specialite specialite = specialiteRepository
                    .findById(request.specialiteId())
                    .orElseThrow(() ->
                            new SpecialiteIntrouvableException("Spécialité introuvable."));
            docteur.setSpecialite(specialite);
        }

        if (request.departementId() != null) {
            Departement departement = departementRepository
                    .findById(request.departementId())
                    .orElseThrow(() ->
                            new DepartementIntrouvableException("Département introuvable."));
            docteur.setDepartement(departement);
        }

        log.info("Médecin mis à jour: {} hôpital: {}", doctorId, hopitalId);

        return new CreerDocteurResponse(
                docteur.getDocteurId(),
                docteur.getNom(),
                docteur.getPrenom(),
                docteur.getNumeroDeLicence(),
                docteur.getEmail(),
                docteur.getSpecialite().getNom(),
                docteur.getDepartement().getNom(),
                docteur.getHopitalId(),
                docteur.getDateEmbauche()
        );
    }

    @Override
    public List<CreerDocteurResponse> rechercherDoctors(UUID hopitalId) {
        return docteurRepository.findByHopitalId(hopitalId)
                .stream()
                .map(d -> new CreerDocteurResponse(
                        d.getDocteurId(),
                        d.getNom(),
                        d.getPrenom(),
                        d.getNumeroDeLicence(),
                        d.getEmail(),
                        d.getSpecialite().getNom(),
                        d.getDepartement().getNom(),
                        d.getHopitalId(),
                        d.getDateEmbauche()
                ))
                .toList();
    }

    @Override
    public CreerDocteurResponse getDoctorById(UUID doctorId, UUID hopitalId) {
        Docteur docteur = docteurRepository
                .findByDocteurIdAndHopitalId(doctorId, hopitalId)
                .orElseThrow(() ->
                        new MedecinIntrouvableException("Médecin introuvable."));

        log.info("Récupération médecin: {} hôpital: {}", doctorId, hopitalId);

        return new CreerDocteurResponse(
                docteur.getDocteurId(),
                docteur.getNom(),
                docteur.getPrenom(),
                docteur.getNumeroDeLicence(),
                docteur.getEmail(),
                docteur.getSpecialite().getNom(),
                docteur.getDepartement().getNom(),
                docteur.getHopitalId(),
                docteur.getDateEmbauche()
        );
    }
}
