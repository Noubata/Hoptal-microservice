package com.hms.doctorservice.dtos.requests;

import java.util.UUID;

public record UpdateDoctorRequest(
        String nom,
        String prenom,
        String email,
        String numeroDeTelephone,
        UUID specialiteId,
        UUID departementId
) {}
