package com.training.practice.dto.backend;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"id"},ignoreUnknown = true)
public class PostmanRequest {
    private Long id;
    @JsonProperty("name")
    private String userPostmanName;
    @JsonProperty("email")
    private String userPostmanEmail;
    @JsonProperty("status")
    private String userPostmanStatus;
    @JsonProperty("roles")
    private Map<String, Boolean> userPostmanRoles;
}
