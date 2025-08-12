package com.training.practice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.practice.PracticeApplication;
import com.training.practice.config.SecurityConfig;
import com.training.practice.dto.backend.PostmanRequest;
import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.dto.external.PostmanClientResponse;
import com.training.practice.service.PostmanClientService;

/**
 * Simple Controller tests for PostmanController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostmanController.class)
@ContextConfiguration(classes = PracticeApplication.class)
@Import(SecurityConfig.class)
@DisplayName("PostmanController Tests")
class PostmanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostmanClientService postmanClientService;

    private PostmanResponse mockResponse;
    private PostmanRequest mockRequest;
    private PostmanClientResponse mockClientResponse;

    @BeforeEach
    void setUp() {
        Map<String, Boolean> roles = new HashMap<>();
        roles.put("USER", true);
        roles.put("ADMIN", false);

        mockResponse = new PostmanResponse(1L, "John Doe", "john@example.com", "ACTIVE", roles, null, null);
        mockRequest = new PostmanRequest(null, "Jane Doe", "jane@example.com", "ACTIVE", roles);
        
        mockClientResponse = new PostmanClientResponse(123L, "John Doe", "john@example.com", "active", roles, null, null);
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() throws Exception {
        when(postmanClientService.getUserById(123L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/external/postman/users/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"))
                .andExpect(jsonPath("$.data.roles.USER").value(true))
                .andExpect(jsonPath("$.data.roles.ADMIN").value(false));
        
        verify(postmanClientService, times(1)).getUserById(123L);
    }

    @Test
    @DisplayName("Should get all users successfully")
    void shouldGetAllUsersSuccessfully() throws Exception {
        // Given
        // List<PostmanResponse> responses = Arrays.asList(mockResponse);
        // given(postmanClientService.getAllUsers()).willReturn(responses);
        
        // When
        when(postmanClientService.getAllUsers()).thenReturn(Arrays.asList(mockResponse));

        // When & Then
        mockMvc.perform(get("/api/external/postman/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
        
        verify(postmanClientService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() throws Exception {
        // Given
        given(postmanClientService.createUser(any(PostmanRequest.class))).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/external/postman/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Should handle invalid JSON gracefully")
    void shouldHandleInvalidJsonGracefully() throws Exception {
        // Given - Invalid JSON
        String invalidJson = "{ \"invalid\": ";

        // When & Then
        mockMvc.perform(post("/api/external/postman/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().is5xxServerError());
    }
}
