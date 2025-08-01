package com.training.practice.dto.backend;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostmanResponse {
    private Long id;
    @JsonProperty("name")
    private String userPostmanName;
    @JsonProperty("email")
    private String userPostmanEmail;
    @JsonProperty("status")
    private String userPostmanStatus;
    @JsonProperty("roles")
    private Map<String, Boolean> userPostmanRoles;
    private String createdAt;
    private String updatedAt;
}
