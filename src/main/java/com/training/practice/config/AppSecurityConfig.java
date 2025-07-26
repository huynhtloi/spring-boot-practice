package com.training.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.security")
public class AppSecurityConfig {
    private Jwt jwt = new Jwt();
    private Cors cors = new Cors();
    
    @Data
    public static class Jwt {
        private String secret = "mySecretKey";
        private long expiration = 86400000; // 24 hours
    }
    
    @Data
    public static class Cors {
        private String[] allowedOrigins = {
            "http://localhost:3000",
            "http://localhost:4200"
        };
        private String[] allowedMethods = {
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        };
    }
}
