package com.hms.doctorservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DepartementIntrouvableException.class)
    public ResponseEntity<String> handleDepartementException(DepartementIntrouvableException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(MedecinIntrouvableException.class)
    public ResponseEntity<String> handleMedecinException(MedecinIntrouvableException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(SpecialiteIntrouvableException.class)
    public ResponseEntity<String> handleSpecialiteException(SpecialiteIntrouvableException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(TokenManquantException.class)
    public ResponseEntity<String> handleTokenManquantException(TokenManquantException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(NumeroLicenceExisteDejaException.class)
    public ResponseEntity<String> handleNumeroLicenceExisteDejaException(NumeroLicenceExisteDejaException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
