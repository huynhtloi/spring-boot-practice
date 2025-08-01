package com.training.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.practice.dto.ApiResponseDTO;
import com.training.practice.dto.backend.PostmanRequest;
import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.service.PostmanClientService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/external/postman")
@RequiredArgsConstructor
@Slf4j
public class PostmanController {
    
    private final PostmanClientService postmanService;
    
    /**
     * GET /api/external/postman/users - Get all users
     * Demonstrates @GetExchange usage
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponseDTO<List<PostmanResponse>>> getAllUsers(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("GET - Fetching all users from Postman API - Request ID: {}", requestId);
        
        List<PostmanResponse> users = postmanService.getAllUsers();
        ApiResponseDTO<List<PostmanResponse>> response = ApiResponseDTO.success("Users fetched successfully", users);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/external/postman/users/{id} - Get user by ID
     * Demonstrates @GetExchange with @PathVariable
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<PostmanResponse>> getUserById(
            @PathVariable Long id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("GET - Fetching user by ID: {} - Request ID: {}", id, requestId);
        
        PostmanResponse user = postmanService.getUserById(id);

        ApiResponseDTO<PostmanResponse> response = ApiResponseDTO.success("User fetched successfully", user);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/external/postman/users?role=admin - Get users by role
     * Demonstrates @GetExchange with @RequestParam
     */
    @GetMapping("/users/by-role")
    public ResponseEntity<ApiResponseDTO<List<PostmanResponse>>> getUsersByRole(
            @RequestParam String role,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("GET - Fetching users by role: {} - Request ID: {}", role, requestId);
        
        List<PostmanResponse> users = postmanService.getUsersByRole(role);
        ApiResponseDTO<List<PostmanResponse>> response = ApiResponseDTO.success("Users by role fetched successfully", users);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/external/postman/users - Create new user
     * Demonstrates @PostExchange with @RequestBody
     */
    @PostMapping("/users")
    public ResponseEntity<ApiResponseDTO<PostmanResponse>> createUser(
            @Valid @RequestBody PostmanRequest request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("POST - Creating user: {} - Request ID: {}", request.getUserPostmanName(), requestId);
        
        PostmanResponse user = postmanService.createUser(request);
        ApiResponseDTO<PostmanResponse> response = ApiResponseDTO.success("User created successfully", user);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * PUT /api/external/postman/users/{id} - Full update user
     * Demonstrates @PutExchange with @PathVariable and @RequestBody
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<PostmanResponse>> updateFullUser(
            @PathVariable Long id,
            @Valid @RequestBody PostmanRequest request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("PUT - Full update user: {} - Request ID: {}", id, requestId);
        
        PostmanResponse user = postmanService.updateFullUser(id, request);
        ApiResponseDTO<PostmanResponse> response = ApiResponseDTO.success("User updated successfully", user);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * PATCH /api/external/postman/users/{id} - Partial update user
     * Demonstrates @PatchExchange with @PathVariable and @RequestBody
     */
    @PatchMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<PostmanResponse>> updatePartialUser(
            @PathVariable Long id,
            @RequestBody PostmanRequest request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("PATCH - Partial update user: {} - Request ID: {}", id, requestId);
        
        PostmanResponse user = postmanService.updatePartialUser(id, request);
        ApiResponseDTO<PostmanResponse> response = ApiResponseDTO.success("User partially updated successfully", user);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * DELETE /api/external/postman/users/{id} - Delete user
     * Demonstrates @DeleteExchange with @PathVariable
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(
            @PathVariable Long id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("DELETE - Deleting user: {} - Request ID: {}", id, requestId);
        
        postmanService.deleteUser(id);
        ApiResponseDTO<Void> response = ApiResponseDTO.success("User deleted successfully", null);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/external/postman/users/{id}/permissions - Get user permissions
     * Demonstrates @GetExchange with @PathVariable returning List<String>
     */
    @GetMapping("/users/{id}/permissions")
    public ResponseEntity<ApiResponseDTO<List<String>>> getUserPermissions(
            @PathVariable Long id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("GET - Fetching permissions for user: {} - Request ID: {}", id, requestId);
        
        List<String> permissions = postmanService.getUserPermissions(id);
        ApiResponseDTO<List<String>> response = ApiResponseDTO.success("User permissions fetched successfully", permissions);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/external/postman/users/{id}/roles - Assign role to user
     * Demonstrates @PostExchange with @PathVariable and @RequestParam
     */
    @PostMapping("/users/{id}/roles")
    public ResponseEntity<ApiResponseDTO<PostmanResponse>> assignRole(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("POST - Assigning role {} to user: {} - Request ID: {}", role, id, requestId);
        
        PostmanResponse user = postmanService.assignRole(id, role);
        ApiResponseDTO<PostmanResponse> response = ApiResponseDTO.success("Role assigned successfully", user);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
}
