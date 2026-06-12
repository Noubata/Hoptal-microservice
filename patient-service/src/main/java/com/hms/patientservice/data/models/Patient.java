package com.hms.patientservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "patients",
        indexes = {
                @Index(name = "idx_patient_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_patient_nom_hopital",
                        columnList = "nom, prenom, hopital_id"),
                @Index(name = "idx_patient_telephone",
                        columnList = "numero_de_telephone, hopital_id"),
                @Index(name = "idx_patient_dossier",
                        columnList = "numero_dossier, hopital_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID patientId;
    @Column(nullable = false)
    private UUID hopitalId;
    @Column(nullable = false, unique = true)
    private String numeroDossier;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private LocalDate dateDeNaissance;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexe sexe;
    @Column
    private String email;
    @Column
    private String numeroDeTelephone;
    @Column
    private String adresse;
    @Column
    private String quartier;
    @Column
    private String ville;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupeSanguin groupeSanguin;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPatient statutPatient = StatutPatient.ACTIF;
    @Column
    private String nomContactUrgence;
    @Column
    private String telephoneContactUrgence;
    @Column
    private String emailContactUrgence;
    @Column(updatable = false)
    private LocalDateTime dateCreation;
    @Column
    private LocalDateTime dateMiseAJour;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateMiseAJour = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateMiseAJour = LocalDateTime.now();
    }
}
