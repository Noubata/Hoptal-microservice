package com.hms.doctorservice.exceptions;

import jakarta.validation.constraints.NotBlank;

public class NumeroLicenceExisteDejaException extends RuntimeException {
    public NumeroLicenceExisteDejaException(@NotBlank(message = "Le numéro de licence est obligatoire") String message) {
        super(message);
    }
}
