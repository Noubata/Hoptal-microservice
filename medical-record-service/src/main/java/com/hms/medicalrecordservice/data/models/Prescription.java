package com.hms.medicalrecordservice.data.models;

import com.hms.medicalrecordservice.data.models.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "prescriptions",
        indexes = {
                @Index(name = "idx_prescription_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_prescription_patient",
                        columnList = "patient_id"),
                @Index(name = "idx_prescription_releve",
                        columnList = "releve_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID prescriptionId;
    @Column(nullable = false)
    private UUID hopitalId;
    @Column(nullable = false)
    private UUID patientId;
    @ManyToOne
    @JoinColumn(name = "releve_id", nullable = false)
    private ReleveMedicale releveMedicale;
    @Column(nullable = false)
    private String nomDuMedicament;
    @Column(nullable = false)
    private String dosage;
    @Column(nullable = false)
    private String frequence;
    @Column(nullable = false)
    private String duree;
    @Column
    private String instructions;
    @Column(nullable = false)
    private boolean renouvellable = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPrescription statut = StatutPrescription.ACTIVE;
    @Column(nullable = false)
    private LocalDateTime dateDePrescription;
    @Column(updatable = false)
    private LocalDateTime dateCreation;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
