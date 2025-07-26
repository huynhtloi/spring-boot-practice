package com.training.practice.dto;

import com.training.practice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private String id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SubjectDTO> subjects;
    
    // Static factory method to convert from Entity
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .subjects(user.getSubjects() != null ? 
                    user.getSubjects().stream()
                        .map(SubjectDTO::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
