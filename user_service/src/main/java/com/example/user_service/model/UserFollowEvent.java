package com.example.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFollowEvent {
    private String followerId;
    private String followingId;
}
