package com.hms.doctorservice.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departements")
@Getter
@Setter
@NoArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID departementId;

    @Column(nullable = false)
    private UUID hopitalId;

    @Column(nullable = false)
    private String nom;

    @Column
    private String description;

    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
