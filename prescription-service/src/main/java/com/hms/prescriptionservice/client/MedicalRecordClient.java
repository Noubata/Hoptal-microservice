package com.hms.prescriptionservice.client;

import com.hms.prescriptionservice.dtos.responses.PrescriptionActiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MedicalRecordClient {

    private final RestTemplate restTemplate;

    @Value("${medical-record-service.url}")
    private String medicalRecordUrl;

    public List<PrescriptionActiveResponse> getPrescriptionsPatient(
            UUID patientId, UUID hopitalId) {

        String url = medicalRecordUrl +
                "/api/releves/patient/" + patientId +
                "/prescriptions?hopitalId=" + hopitalId;

        PrescriptionActiveResponse[] response =
                restTemplate.getForObject(
                        url, PrescriptionActiveResponse[].class);

        return response != null ? List.of(response) : List.of();
    }

    public List<String> getAllergiesPatient(
            UUID patientId, UUID hopitalId) {

        String url = medicalRecordUrl +
                "/api/patients/" + patientId +
                "/allergies/substances?hopitalId=" + hopitalId;

        String[] response =
                restTemplate.getForObject(url, String[].class);

        return response != null ? List.of(response) : List.of();
    }
}