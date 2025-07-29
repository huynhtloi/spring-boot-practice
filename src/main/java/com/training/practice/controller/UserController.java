package com.training.practice.controller;

import com.training.practice.dto.ApiResponseDTO;
import com.training.practice.dto.SubjectCreateDTO;
import com.training.practice.dto.UserCreateDTO;
import com.training.practice.dto.UserDTO;
import com.training.practice.dto.UserUpdateDTO;
import com.training.practice.entity.User;
import com.training.practice.service.FeatureToggleService;
import com.training.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    
    private final UserService userService;
    private final FeatureToggleService featureToggleService;
    
    // CREATE - POST with @RequestBody and @RequestHeader
    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(
            @Valid @RequestBody UserCreateDTO request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @RequestHeader(value = "X-Client-Version", defaultValue = "1.0") String clientVersion) {
        
        log.info("Creating user with request ID: {} and client version: {}", requestId, clientVersion);
        
        UserDTO userResponse = userService.createUser(request);
        ApiResponseDTO<UserDTO> response = ApiResponseDTO.success("User created successfully", userResponse);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // READ - GET by ID with @PathVariable
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserById(
            @PathVariable String id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Fetching user by ID: {} with request ID: {}", id, requestId);
        
        return userService.getUserById(id)
                .map(user -> {
                    ApiResponseDTO<UserDTO> response = ApiResponseDTO.success(user);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponseDTO<UserDTO> response = ApiResponseDTO.error("User not found with ID: " + id);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.notFound().build();
                });
    }
    
    // READ - GET all users with @RequestParam for pagination
    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<Page<UserDTO>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String sortDirection,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        // Use config-based default values
        int pageSize = size != null ? Math.min(size, featureToggleService.getMaxPageSize()) : featureToggleService.getDefaultPageSize();
        
        log.info("Fetching users with pagination - page: {}, size: {}, sort: {} {} (config-based)", 
                page, pageSize, sortBy, sortDirection);
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));
            
            Page<UserDTO> users = userService.getUsersWithPagination(pageable);
            ApiResponseDTO<Page<UserDTO>> response = ApiResponseDTO.success(users);
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching users: {}", e.getMessage());
            ApiResponseDTO<Page<UserDTO>> response = ApiResponseDTO.error(e.getMessage());
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // READ - GET users by department with @RequestParam
    @GetMapping("/department")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByDepartment(
            @RequestParam("dept") String department,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Fetching users by department: {} with request ID: {}", department, requestId);
        
        List<UserDTO> users = userService.getUsersByDepartment(department);
        ApiResponseDTO<List<UserDTO>> response = ApiResponseDTO.success(users);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // Read - GET users by status with @PathVariable
    @GetMapping("/status/{status}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByStatus(
            @PathVariable User.UserStatus status,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Fetching users by status: {} with request ID: {}", status, requestId);
        
        List<UserDTO> users = userService.getUsersByStatus(status);
        ApiResponseDTO<List<UserDTO>> response = ApiResponseDTO.success(users);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    // ReAD - GET users by department and status with @PathVariable
    @GetMapping("/department/{department}/status/{status}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByDepartmentAndStatus(
            @PathVariable String department,
            @PathVariable User.UserStatus status,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        log.info("Fetching users by department: {} and status: {} with request ID: {}", department, status, requestId);
        List<UserDTO> users = userService.getUsersByDepartmentAndStatus(department, status);
        ApiResponseDTO<List<UserDTO>> response = ApiResponseDTO.success(users);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // READ - GET search users by name with @RequestParam
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> searchUsers(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "50") @Min(1) @Max(100) int limit,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Searching users with query: {} and limit: {} with request ID: {}", query, limit, requestId);
        
        List<UserDTO> users = userService.searchUsersByName(query);
        // Limit results
        List<UserDTO> limitedUsers = users.stream()
                .limit(limit)
                .toList();
        
        ApiResponseDTO<List<UserDTO>> response = ApiResponseDTO.success(limitedUsers);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // UPDATE - PUT with @PathVariable and @RequestBody
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserUpdateDTO request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @RequestHeader(value = "X-Client-Version", defaultValue = "1.0") String clientVersion) {
        
        log.info("Updating user ID: {} with request ID: {} and client version: {}", id, requestId, clientVersion);
        
        UserDTO userResponse = userService.updateUser(id, request);
        ApiResponseDTO<UserDTO> response = ApiResponseDTO.success("User updated successfully", userResponse);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // PARTIAL UPDATE - PATCH with @PathVariable and @RequestParam
    @PatchMapping("/{id}/status")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUserStatus(
            @PathVariable String id,
            @RequestParam("status") User.UserStatus status,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Updating user status for ID: {} to status: {} with request ID: {}", id, status, requestId);
        
        try {
            UserUpdateDTO request = UserUpdateDTO.builder()
                    .status(status)
                    .build();
            
            UserDTO userResponse = userService.updateUser(id, request);
            ApiResponseDTO<UserDTO> response = ApiResponseDTO.success("User status updated successfully", userResponse);
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error updating user status: {}", e.getMessage());
            ApiResponseDTO<UserDTO> response = ApiResponseDTO.error(e.getMessage());
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}/subject")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserDTO>> addNewSubjectToUser(
            @PathVariable String id,
            @Valid @RequestBody SubjectCreateDTO request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        log.info("Adding new subject to user ID: {} with request ID: {}", id, requestId);
        
        UserDTO userResponse = userService.addNewSubjectToUser(id, request);
        ApiResponseDTO<UserDTO> response = ApiResponseDTO.success("Subject added to user successfully", userResponse);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // DELETE - DELETE with @PathVariable
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(
            @PathVariable String id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @RequestHeader(value = "X-Confirm-Delete", defaultValue = "false") boolean confirmDelete) {
        
        log.info("Deleting user ID: {} with request ID: {} and confirm: {}", id, requestId, confirmDelete);
        
        if (!confirmDelete) {
            ApiResponseDTO<Void> response = ApiResponseDTO.error("Delete confirmation required. Send X-Confirm-Delete: true header");
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            userService.deleteUser(id);
            ApiResponseDTO<Void> response = ApiResponseDTO.success("User deleted successfully", null);
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error deleting user: {}", e.getMessage());
            ApiResponseDTO<Void> response = ApiResponseDTO.error(e.getMessage());
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // STATISTICS - GET count by status
    @GetMapping("/stats/count")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<Long>> getUserCountByStatus(
            @RequestParam("status") User.UserStatus status,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Getting user count by status: {} with request ID: {}", status, requestId);
        
        long count = userService.getUserCountByStatus(status);
        ApiResponseDTO<Long> response = ApiResponseDTO.success("User count retrieved successfully", count);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
}
