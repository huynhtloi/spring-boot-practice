package com.training.practice.exception;

/**
 * Exception thrown when subject validation fails
 */
public class SubjectValidationException extends RuntimeException {
    
    public SubjectValidationException(String message) {
        super(message);
    }
    
    public SubjectValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
