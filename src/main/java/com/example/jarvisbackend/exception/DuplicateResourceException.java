package com.example.jarvisbackend.exception;

/**
 * Exception thrown when attempting to create a duplicate resource (e.g., email/phone already exists).
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
