package com.hms.doctorservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "specialites")
@Getter
@Setter
@NoArgsConstructor
public class Specialite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID specialiteId;

    @Column(nullable = false)
    private UUID hopitalId;

    @Column(nullable = false, unique = false)
    private String nom;

    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
