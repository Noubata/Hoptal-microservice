package com.hms.resultlaboservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResultatDejaSaisiException.class)
    public ResponseEntity<String> handleResultatDejaSaisiException(ResultatDejaSaisiException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(ResultatIntrouvableException.class)
    public ResponseEntity<String> handleResultatIntrouvableException(ResultatIntrouvableException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(TokenManquantException.class)
    public ResponseEntity<String> handleTokenManquantException(TokenManquantException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
