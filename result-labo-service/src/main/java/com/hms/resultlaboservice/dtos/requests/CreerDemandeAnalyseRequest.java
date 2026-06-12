package com.hms.resultlaboservice.dtos.requests;

import java.util.UUID;

public record CreerDemandeAnalyseRequest(
        UUID laborantinId,
        UUID patientId,
        String typeAnalyse
) {}
