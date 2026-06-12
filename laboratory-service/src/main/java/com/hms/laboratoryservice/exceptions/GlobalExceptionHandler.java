package com.hms.laboratoryservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LaborantinIntrouvableException.class)
    public ResponseEntity<String> handleLaborantinIntrouvableException(LaborantinIntrouvableException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(LaborantinExisteDejaException.class)
    public ResponseEntity<String> handleLaborantinExisteDejaException(LaborantinExisteDejaException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
    @ExceptionHandler(ServiceIntrouvableException.class)
    public ResponseEntity<String> handleServiceIntrouvableException(ServiceIntrouvableException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(TokenManquantException.class)
    public ResponseEntity<String> handleTokenManquantException(TokenManquantException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
}
