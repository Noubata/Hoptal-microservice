package com.hms.laboratoryservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// DemandeAnalyse.java
@Entity
@Table(
        name = "demandes_analyse",
        indexes = {
                @Index(name = "idx_demande_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_demande_laborantin",
                        columnList = "laborantin_id"),
                @Index(name = "idx_demande_patient",
                        columnList = "patient_id"),
                @Index(name = "idx_demande_statut",
                        columnList = "statut")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class DemandeAnalyse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID demandeId;

    @Column(nullable = false)
    private UUID hopitalId;

    // From patient-service — plain UUID
    @Column(nullable = false)
    private UUID patientId;

    // From laboratory-service own table
    @Column(nullable = false)
    private UUID laborantinId;

    @Column(nullable = false)
    private String typeAnalyse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
