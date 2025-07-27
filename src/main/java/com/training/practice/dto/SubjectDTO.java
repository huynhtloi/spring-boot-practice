package com.training.practice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private String id;
    private String name;
    private String description;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
