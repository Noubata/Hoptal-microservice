package com.hms.doctorservice.data.models;

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
        name = "docteurs",
        indexes = {
                @Index(name = "idx_docteur_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_docteur_licence_hopital",
                        columnList = "numero_de_licence, hopital_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"numero_de_licence", "hopital_id"},
                        name = "uk_licence_hopital"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Docteur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID docteurId;
    @Column(nullable = false)
    private UUID hopitalId;
    // Links to the user account in auth-service
    // Not a @ManyToOne because User is in a different service/database
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String numeroDeLicence;
    @Column
    private String email;
    @Column
    private String numeroDeTelephone;
    @Column(nullable = false)
    private LocalDate dateEmbauche;
    @ManyToOne
    @JoinColumn(name = "specialite_id", nullable = false)
    private Specialite specialite;
    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;
    private boolean active = true;
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
