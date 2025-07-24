package com.training.practice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingRequest {
    private String name;
    private String language;
    private boolean formal;
}
