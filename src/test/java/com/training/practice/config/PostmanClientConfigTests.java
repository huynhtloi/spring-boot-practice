package com.training.practice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
class PostmanClientConfigTests {

    @Autowired
    private PostmanClientConfig postmanClientConfig;

    @Test
    void givenPostmanConfig_whenRunningApp_thenLoadApplicationContext() {
        assertAll("Validate postman client config",
            () -> {
                assertThat(postmanClientConfig).isNotNull();
                assertThat(postmanClientConfig.getBaseUrl()).isEqualTo("http://localhost:9999");
            }
        );
    }
}
