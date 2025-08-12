package com.training.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.training.practice.client.PostmanClient;
import com.training.practice.config.PostmanClientConfig;
import com.training.practice.dto.backend.PostmanRequest;
import com.training.practice.dto.backend.PostmanResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@WireMockTest(httpPort = 9999)
class PostmanClientServiceTest {

    @Mock
    private PostmanClient postmanClient;

    @Autowired    
    private PostmanClientService postmanClientService;

    @Autowired
    private PostmanClientConfig postmanClientConfig;

    private PostmanRequest mockRequest;

    @BeforeEach
    void setup() {
        reset();
        mockRequest = new PostmanRequest();
        mockRequest.setUserPostmanName("John Doe");
        mockRequest.setUserPostmanEmail("john.doe@example.com");
        mockRequest.setUserPostmanStatus("ACTIVE");
        Map<String, Boolean> roles = new HashMap<>();
        roles.put("admin", false);
        roles.put("staff", true);
        roles.put("member", true);
        mockRequest.setUserPostmanRoles(roles);
    }

    @Test
    void createUser_shouldReturnMappedInternalUser() {
        Long userId = 12345L;
        stubFor(post(urlEqualTo("/users"))
            .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
            .withRequestBody(equalToJson("""
                {
                    "name": "John Doe",
                    "email": "john.doe@example.com",
                    "status": "ACTIVE",
                    "roles": {
                        "admin": false,
                        "staff": true,
                        "member": true
                    }
                }
            """, true, true))
            .willReturn(aResponse()
                .withStatus(HttpStatus.CREATED.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(String.format("""
                    {
                        "id": "%d",
                        "name": "John Doe",
                        "email": "john.doe@example.com",
                        "status": "ACTIVE",
                        "roles": {
                            "admin": false,
                            "staff": true,
                            "member": true
                        },
                        "createdAt": "2025-07-31T10:00:00Z",
                        "updatedAt": "2025-07-31T10:00:00Z"
                    }
                    """, userId))));
        System.out.println(WireMock.findAll(postRequestedFor(anyUrl())));
        PostmanResponse result = postmanClientService.createUser(mockRequest);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(mockRequest.getUserPostmanName(), result.getUserPostmanName());
        assertEquals(mockRequest.getUserPostmanEmail(), result.getUserPostmanEmail());
        assertEquals(String.format("User with %s", mockRequest.getUserPostmanStatus()), result.getUserPostmanStatus());
        assertEquals("2025-07-31T10:00:00Z", result.getCreatedAt());
        assertEquals("2025-07-31T10:00:00Z", result.getUpdatedAt());

        verify(postRequestedFor(urlEqualTo("/users"))
            .withRequestBody(equalToJson(
                "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"roles\": {\n" +
                "    \"admin\": false,\n" +
                "    \"staff\": true,\n" +
                "    \"member\": true\n" +
                "  }\n" +
                "}", true, true)));
    }
}
