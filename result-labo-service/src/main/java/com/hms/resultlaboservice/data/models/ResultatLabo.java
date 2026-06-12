package com.hms.resultlaboservice.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "resultats_labo",
        indexes = {
                @Index(name = "idx_resultat_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_resultat_patient",
                        columnList = "patient_id"),
                @Index(name = "idx_resultat_laborantin",
                        columnList = "laborantin_id"),
                @Index(name = "idx_resultat_demande",
                        columnList = "demande_id"),
                @Index(name = "idx_resultat_anomalie",
                        columnList = "anomalie")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ResultatLabo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID resultatId;
    @Column(nullable = false)
    private UUID hopitalId;
    // From laboratory-service — plain UUID
    @Column(nullable = false)
    private UUID demandeId;
    // From patient-service — plain UUID
    @Column(nullable = false)
    private UUID patientId;
    // From laboratory-service — plain UUID
    @Column(nullable = false)
    private UUID laborantinId;
    @Column(nullable = false)
    private String typeAnalyse;
    @Column
    private String resultat;
    @Column
    private String valeurNormale;
    @Column
    private String unite;
    @Column
    private String commentaire;
    @Column(nullable = false)
    private boolean anomalie = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutResultat statutResultat = StatutResultat.EN_ATTENTE;
    @Column
    private LocalDateTime dateDeTest;
    @Column(updatable = false)
    private LocalDateTime dateCreation;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
