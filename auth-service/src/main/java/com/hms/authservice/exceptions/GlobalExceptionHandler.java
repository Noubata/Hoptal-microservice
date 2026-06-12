package com.hms.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompteDesactiveException.class)
    public ResponseEntity<String> handleCompteDesactiveException(CompteDesactiveException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(UtilisateurIntrouvableException.class)
    public ResponseEntity<String> handleUtilisateurIntrouvableEception(UtilisateurIntrouvableException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(MotDePasseIncorrectException.class)
    public ResponseEntity<String> handleMotDePasseIncorrectException(MotDePasseIncorrectException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(UtilisateurExisteDejaException.class)
    public ResponseEntity<String> handleUtilisateurExisteDejaException(UtilisateurExisteDejaException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(RoleIntrouvableException.class)
    public ResponseEntity<String> handleRoleIntrouvableException(RoleIntrouvableException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(TokenManquantException.class)
    public ResponseEntity<String> handleTokenManquantException(TokenManquantException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(AccesRefuseException.class)
    public ResponseEntity<String> handleAccesRefuseException(AccesRefuseException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(AncienMotDePasseIncorrectException.class)
    public ResponseEntity<String> handleAncienMotDePasseIncorrect(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
