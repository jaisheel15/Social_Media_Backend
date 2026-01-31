package com.example.analytics_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeEvent {
    private String postId;
    private String postUserId;
    private String likedUserId;
}
