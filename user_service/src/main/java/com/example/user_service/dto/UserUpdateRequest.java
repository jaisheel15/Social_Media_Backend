package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Size(max = 500, message = "Bio must not exceed 500 characters")
    private String bio;

    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    private String avatarUrl;

    @Email(message = "Email must be valid")
    private String email;
}
