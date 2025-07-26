package com.training.practice.service;

import com.training.practice.config.DatasourceConfig;
import com.training.practice.config.DatabaseConfig;
import com.training.practice.config.AppSecurityConfig;
import com.training.practice.config.FeaturesConfig;
import com.training.practice.config.PaginationConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class ConfigService {
    
    @Autowired
    private DatasourceConfig datasourceConfig;
    
    @Autowired
    private DatabaseConfig databaseConfig;
    
    @Autowired
    private AppSecurityConfig appSecurityConfig;
    
    @Autowired
    private FeaturesConfig featuresConfig;
    
    @Autowired
    private PaginationConfig paginationConfig;
    
    @PostConstruct
    public void displayInjectedConfigs() {
        log.info("=== Configuration Injection Demo ===");
        log.info("Datasource URL: {}", datasourceConfig.getUrl());
        log.info("Database Pool Size: {}", databaseConfig.getMaximumPoolSize());
        log.info("JWT Secret: {}", appSecurityConfig.getJwt().getSecret());
        log.info("Features Logging: {}", featuresConfig.isEnableLogging());
        log.info("Pagination Default Size: {}", paginationConfig.getDefaultPageSize());
        log.info("=== All configs successfully injected! ===");
    }
    
    public String getDatabaseInfo() {
        return String.format("Database: %s with pool size %d", 
                datasourceConfig.getUrl(), 
                databaseConfig.getMaximumPoolSize());
    }
    
    public boolean isFeatureEnabled(String featureName) {
        return switch (featureName) {
            case "logging" -> featuresConfig.isEnableLogging();
            default -> false;
        };
    }
    
    public int getConfiguredPageSize() {
        return paginationConfig.getDefaultPageSize();
    }
}
