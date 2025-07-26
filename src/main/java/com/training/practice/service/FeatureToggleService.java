package com.training.practice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.practice.config.FeaturesConfig;
import com.training.practice.config.PaginationConfig;
import com.training.practice.config.AppSecurityConfig;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class FeatureToggleService {
    
    @Autowired
    private FeaturesConfig featuresConfig;
    
    @Autowired
    private PaginationConfig paginationConfig;
    
    @Autowired
    private AppSecurityConfig appSecurityConfig;
    
    @PostConstruct
    public void logFeatureFlags() {
        log.info("Feature Flags Configuration:");
        log.info("  - Enable Logging: {}", featuresConfig.isEnableLogging());
        log.info("Pagination Configuration:");
        log.info("  - Default Page Size: {}", paginationConfig.getDefaultPageSize());
        log.info("  - Max Page Size: {}", paginationConfig.getMaxPageSize());
    }
    
    public boolean isLoggingEnabled() {
        return featuresConfig.isEnableLogging();
    }
    
    public int getDefaultPageSize() {
        return paginationConfig.getDefaultPageSize();
    }
    
    public int getMaxPageSize() {
        return paginationConfig.getMaxPageSize();
    }
    
    public String getJwtSecret() {
        return appSecurityConfig.getJwt().getSecret();
    }
    
    public long getJwtExpiration() {
        return appSecurityConfig.getJwt().getExpiration();
    }
}
