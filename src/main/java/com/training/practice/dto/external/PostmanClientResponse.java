package com.training.practice.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostmanClientResponse {
    private Long id;
    private String name;
    private String email;
    private String status;
    private Map<String, Boolean> roles;
    private String createdAt;
    private String updatedAt;
}
