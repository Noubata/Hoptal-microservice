package com.hms.doctorservice.service;

import com.hms.doctorservice.dtos.requests.CreerDocteurRequest;
import com.hms.doctorservice.dtos.requests.UpdateDoctorRequest;
import com.hms.doctorservice.dtos.responses.CreerDocteurResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface DocteurService {

    @Transactional
    CreerDocteurResponse creerDoctor(
            CreerDocteurRequest request,
            UUID hopitalId);

    @Transactional
    CreerDocteurResponse updateDoctor(
            UUID doctorId,
            UUID hopitalId,
            UpdateDoctorRequest request);

    List<CreerDocteurResponse> rechercherDoctors(UUID hopitalId);

    CreerDocteurResponse getDoctorById(UUID doctorId, UUID hopitalId);
}
