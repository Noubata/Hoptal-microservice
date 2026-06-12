package com.hms.authservice.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnregisterUtilisateurResponse (UUID userId,
                                             String nomUtilisateur,
                                             String email,
                                             String role,
                                             UUID hopitalId,
                                             LocalDateTime dateCreation){

}