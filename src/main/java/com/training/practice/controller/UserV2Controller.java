package com.training.practice.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.practice.dto.ApiResponseDTO;
import com.training.practice.dto.UserV2DTO;
import com.training.practice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/users/v2")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserV2Controller {
    private final UserService userService;

    // READ - GET by ID with @PathVariable
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<UserV2DTO>> getUserById(
            @PathVariable String id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Fetching user by ID: {} with request ID: {}", id, requestId);
        
        return userService.getUserByIdV2(id)
                .map(user -> {
                    ApiResponseDTO<UserV2DTO> response = ApiResponseDTO.success(user);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponseDTO<UserV2DTO> response = ApiResponseDTO.error("User not found with ID: " + id);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.notFound().build();
                });
    }
}
