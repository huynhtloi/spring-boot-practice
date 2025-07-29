package com.training.practice.exception;

import com.training.practice.dto.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Global exception handler for all controllers
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleUserNotFound(
            UserNotFoundException ex, 
            HttpServletRequest request) {
        
        log.error("User not found: {}", ex.getMessage());
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error(ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleSubjectNotFound(
            SubjectNotFoundException ex, 
            HttpServletRequest request) {
        
        log.error("Subject not found: {}", ex.getMessage());
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error(ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex, 
            HttpServletRequest request) {
        
        log.error("Email already exists: {}", ex.getMessage());
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error(ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(SubjectValidationException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleSubjectValidation(
            SubjectValidationException ex, 
            HttpServletRequest request) {
        
        log.error("Subject validation failed: {}", ex.getMessage());
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error(ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleValidationErrors(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
        
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.error("Validation failed: {}", errorMessage);
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error("Validation failed: " + errorMessage);
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleConstraintViolation(
            ConstraintViolationException ex, 
            HttpServletRequest request) {
        
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        
        log.error("Constraint validation failed: {}", errorMessage);
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error("Validation failed: " + errorMessage);
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleIllegalArgument(
            IllegalArgumentException ex, 
            HttpServletRequest request) {
        
        log.error("Illegal argument: {}", ex.getMessage());
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error("Invalid request: " + ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleRuntimeException(
            RuntimeException ex, 
            HttpServletRequest request) {
        
        log.error("Runtime exception occurred: {}", ex.getMessage(), ex);
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error("An error occurred: " + ex.getMessage());
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGenericException(
            Exception ex, 
            HttpServletRequest request) {
        
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        ApiResponseDTO<Void> response = ApiResponseDTO.error("An unexpected error occurred");
        response.setRequestId(getRequestId(request));
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String getRequestId(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-ID");
        return requestId != null ? requestId : UUID.randomUUID().toString();
    }
}
