package com.training.practice.controller;

import com.training.practice.config.DatasourceConfig;
import com.training.practice.config.DatabaseConfig;
import com.training.practice.config.AppSecurityConfig;
import com.training.practice.dto.ApiResponseDTO;
import com.training.practice.service.FeatureToggleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {
    
    private final DatasourceConfig datasourceConfig;
    private final DatabaseConfig databaseConfig;
    private final AppSecurityConfig appSecurityConfig;
    private final FeatureToggleService featureToggleService;
    
    @GetMapping("/features")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getFeatureFlags(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Fetching feature flags configuration");
        
        Map<String, Object> config = Map.of(
                "enableLogging", featureToggleService.isLoggingEnabled(),
                "pagination", Map.of(
                        "defaultPageSize", featureToggleService.getDefaultPageSize(),
                        "maxPageSize", featureToggleService.getMaxPageSize()
                ),
                "datasource", Map.of(
                        "url", datasourceConfig.getUrl(),
                        "username", datasourceConfig.getUsername(),
                        "driverClassName", datasourceConfig.getDriverClassName()
                ),
                "security", Map.of(
                        "jwtExpiration", featureToggleService.getJwtExpiration(),
                        "allowedOrigins", appSecurityConfig.getCors().getAllowedOrigins(),
                        "allowedMethods", appSecurityConfig.getCors().getAllowedMethods()
                ),
                "database", Map.of(
                        "connectionTimeout", databaseConfig.getConnectionTimeout(),
                        "maximumPoolSize", databaseConfig.getMaximumPoolSize(),
                        "minimumIdle", databaseConfig.getMinimumIdle()
                )
        );
        
        ApiResponseDTO<Map<String, Object>> response = ApiResponseDTO.success("Configuration retrieved successfully", config);
        response.setRequestId(requestId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/features/logging")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<String>> toggleLogging(
            @RequestParam boolean enabled,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        log.info("Toggling logging feature to: {}", enabled);
        
        String message = String.format("Logging is currently %s. Feature toggle received: %s", 
                featureToggleService.isLoggingEnabled() ? "enabled" : "disabled", 
                enabled);
        
        ApiResponseDTO<String> response = ApiResponseDTO.success(message, null);
        response.setRequestId(requestId);
        
        return ResponseEntity.ok(response);
    }
}
