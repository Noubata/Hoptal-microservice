package com.hms.patientservice.dtos.responses;

import java.util.List;

public record DossierCompletResponse(
        CreerPatientResponse patient,
        List<AjouterAllergieResponse> allergies,
        List<AjouterAntecedentResponse> antecedents
) {}
