package com.training.practice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.TestPropertySource;

import com.training.practice.client.PostmanClient;
import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.dto.external.PostmanClientResponse;
import com.training.practice.mapper.PostmanClientMapper;
import com.training.practice.service.PostmanClientService;

/**
 * Simple Integration tests for PostmanClientService without external dependencies
 */
@SpringBootTest
@TestPropertySource(properties = {
    "postman.client.base-url=http://localhost:3000"
})
@DisplayName("PostmanClientService Simple Integration Tests")
class PostmanClientSimpleIntegrationTest {

    @Autowired
    private PostmanClientService postmanClientService;

    @Autowired
    private PostmanClientMapper postmanClientMapper;

    @MockitoBean
    private PostmanClient postmanClient;

    private PostmanResponse mockResponse;
    private PostmanClientResponse mockClientResponse;

    @BeforeEach
    void setUp() {
        Map<String, Boolean> roles = new HashMap<>();
        roles.put("USER", true);

        mockResponse = new PostmanResponse(1L, "Integration Test User", "integration@test.com", "ACTIVE", roles, null, null);
        mockClientResponse = new PostmanClientResponse(1L, "Integration Test User", "integration@test.com", "active", roles, null, null);
    }

    @Test
    @DisplayName("Should verify service and mapper integration")
    void shouldVerifyServiceAndMapperIntegration() {
        assertThat(postmanClientService).isNotNull();
        assertThat(postmanClientMapper).isNotNull();
        assertThat(postmanClient).isNotNull();
    }

    @Test
    @DisplayName("Should verify mapper functionality")
    void shouldVerifyMapperFunctionality() {
        var internalResponse = postmanClientMapper.toBeInternal(mockClientResponse);
        
        assertThat(internalResponse).isNotNull();
        assertThat(internalResponse.getId()).isEqualTo(1L);
        assertThat(internalResponse.getUserPostmanName()).isEqualTo("Integration Test User");
        assertThat(internalResponse.getUserPostmanEmail()).isEqualTo("integration@test.com");
        assertThat(internalResponse.getUserPostmanStatus()).isEqualTo("User with ACTIVE");
        assertThat(internalResponse.getUserPostmanRoles()).containsEntry("USER", true);
    }
}
