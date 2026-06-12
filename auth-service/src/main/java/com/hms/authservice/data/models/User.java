package com.hms.authservice.data.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    @Column(nullable = false)
    private UUID hopitalId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique  = true)
    private String numeroTelephone;
    @Column(nullable = false)
    private String quartier;
    @Column(nullable = false)
    private String nomUtilisateur;
    @Column(nullable = false)
    private String motDePasseHashe;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    private boolean active = true;
    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
