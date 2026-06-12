package com.hms.laboratoryservice.data.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class ServiceLabo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID serviceId;
    private UUID hopitalId;
    private String nom;
    private String description;
}
