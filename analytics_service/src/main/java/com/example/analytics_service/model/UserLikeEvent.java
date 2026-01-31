package com.example.analytics_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeEvent {
    private String userId;
    private String postId;
    private String email;
}
