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
        name = "allergies",
        indexes = {
                @Index(name = "idx_allergie_patient",
                        columnList = "patient_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Allergie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID allergieId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String substance;

    @Column(nullable = false)
    private String severite;

    @Column
    private String reaction;

    @Column
    private LocalDate dateDecouverte;

    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
