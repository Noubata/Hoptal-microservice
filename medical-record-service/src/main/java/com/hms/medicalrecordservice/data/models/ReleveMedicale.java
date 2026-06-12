package com.hms.medicalrecordservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "releves_medicales",
        indexes = {
                @Index(name = "idx_releve_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_releve_patient",
                        columnList = "patient_id"),
                @Index(name = "idx_releve_docteur",
                        columnList = "docteur_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ReleveMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID releveId;
    @Column(nullable = false)
    private UUID hopitalId;
    @Column(nullable = false)
    private UUID patientId;
    @Column(nullable = false)
    private UUID docteurId;
    @Column(nullable = false)
    private String diagnostic;
    @Column
    private String symptomes;
    @Column
    private String notes;
    @Column
    private String typeVisite;
    @Column
    private Integer dureeConsultation;
    @Column(nullable = false)
    private LocalDateTime dateDeVisite;
    @Column(updatable = false)
    private LocalDateTime dateCreation;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
