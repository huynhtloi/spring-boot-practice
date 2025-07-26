package com.training.practice.dto;

import com.training.practice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    
    private String name;
    private String email;
    private String phone;
    private String department;
    private User.UserStatus status;
}
