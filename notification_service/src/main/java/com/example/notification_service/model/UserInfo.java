package com.example.notification_service.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "users_info")
public class UserInfo {
    private String userId;
    private String email;

    
}
