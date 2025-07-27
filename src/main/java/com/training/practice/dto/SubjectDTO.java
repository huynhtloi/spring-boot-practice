package com.training.practice.dto;

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
    // Removed createdAt and updatedAt to hide timestamps in API responses
}
