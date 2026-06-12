package com.hms.authservice.exceptions;

public class UtilisateurExisteDejaException extends RuntimeException {
    public UtilisateurExisteDejaException(String message) {
        super(message);
    }
}
