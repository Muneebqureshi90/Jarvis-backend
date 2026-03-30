package com.example.jarvisbackend.exception;

/**
 * Exception thrown when a requested resource (e.g., User) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
