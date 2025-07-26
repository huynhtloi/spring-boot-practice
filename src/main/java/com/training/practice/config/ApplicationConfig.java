package com.training.practice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    
    private final DatasourceConfig datasourceConfig;
    private final DatabaseConfig databaseConfig;
    private final AppSecurityConfig appSecurityConfig;
    
    @Bean
    @Primary
    public DataSource dataSource() {
        log.info("Configuring DataSource with URL: {}", datasourceConfig.getUrl());
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(datasourceConfig.getUrl());
        config.setUsername(datasourceConfig.getUsername());
        config.setPassword(datasourceConfig.getPassword());
        config.setDriverClassName(datasourceConfig.getDriverClassName());
        
        // Apply database configuration
        config.setConnectionTimeout(databaseConfig.getConnectionTimeout());
        config.setMaximumPoolSize(databaseConfig.getMaximumPoolSize());
        config.setMinimumIdle(databaseConfig.getMinimumIdle());
        
        return new HikariDataSource(config);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Configuring CORS with allowed origins: {}", 
                Arrays.toString(appSecurityConfig.getCors().getAllowedOrigins()));
        
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(appSecurityConfig.getCors().getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList(appSecurityConfig.getCors().getAllowedMethods()));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
