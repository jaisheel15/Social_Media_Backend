package com.example.notification_service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoRequest {
    private String userId;
    private String email;
    private Instant createdAt;
}
