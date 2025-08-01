package com.training.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.training.practice.client.PostmanClient;

@Configuration
public class HttpClientConfig {
    
    @Bean
    public PostmanClient postmanClient(final PostmanClientConfig postmanClientConfig) {
        WebClient webClient = WebClient.builder()
                .baseUrl(postmanClientConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        
        return createClient(webClient, PostmanClient.class);
    }
    
    private <T> T createClient(WebClient webClient, Class<T> clientClass) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        
        return factory.createClient(clientClass);
    }
}
