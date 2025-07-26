package com.training.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.pagination")
public class PaginationConfig {
    private int defaultPageSize = 10;
    private int maxPageSize = 100;
}
