package com.example.user_service.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String bio;
    private String username;
    private String avatarUrl;
    private String email;
}
