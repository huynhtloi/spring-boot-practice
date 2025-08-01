package com.training.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.postman.profile.host")
public class PostmanClientConfig {
    private String baseUrl;
}
