package com.training.practice.exception;

/**
 * Exception thrown when a subject is not found
 */
public class SubjectNotFoundException extends RuntimeException {
    
    public SubjectNotFoundException(String message) {
        super(message);
    }
    
    public SubjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static SubjectNotFoundException withId(String id) {
        return new SubjectNotFoundException("Subject not found with ID: " + id);
    }
}
