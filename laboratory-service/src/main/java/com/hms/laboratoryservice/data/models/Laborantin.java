package com.hms.laboratoryservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "laborantins",
        indexes = {
                @Index(name = "idx_laborantin_hopital",
                        columnList = "hopital_id"),
                @Index(name = "idx_laborantin_service",
                        columnList = "service_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Laborantin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID laborantinId;
    @Column(nullable = false)
    private UUID hopitalId;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column
    private String email;
    @Column
    private String numeroDeTelephone;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceLabo service;
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