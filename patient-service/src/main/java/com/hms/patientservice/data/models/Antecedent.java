package com.hms.patientservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "antecedents",
        indexes = {
                @Index(name = "idx_antecedent_patient",
                        columnList = "patient_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Antecedent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID antecedentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String typeAntecedent;

    @Column(nullable = false)
    private String description;

    @Column
    private LocalDate dateDebut;

    @Column(nullable = false)
    private boolean chronique = false;

    @Column
    private String notes;

    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
