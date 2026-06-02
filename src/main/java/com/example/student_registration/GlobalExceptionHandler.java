package com.example.student_registration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catch raw database or runtime processing bugs across the whole application securely
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleDatabaseErrors(RuntimeException ex) {

        // 1. Log the real, raw technical error securely to your private IntelliJ console terminal
        System.err.println("CRITICAL SECURITY GUARD LOG: " + ex.getMessage());

        // 2. Wrap it up and return a perfectly clean, user-friendly message to the client
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Application Error: The operation could not be completed. Please ensure your input values are correct, mandatory IDs are provided, and your email is unique.");
    }
}