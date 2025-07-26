package com.training.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.database")
public class DatabaseConfig {
    private int connectionTimeout = 30000;
    private int maximumPoolSize = 10;
    private int minimumIdle = 5;
}
