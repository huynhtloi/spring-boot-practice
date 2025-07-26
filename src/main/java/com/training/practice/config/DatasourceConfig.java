package com.training.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.datasource")
public class DatasourceConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
