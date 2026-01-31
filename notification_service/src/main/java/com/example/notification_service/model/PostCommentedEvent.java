package com.example.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentedEvent {
    private String postId;
    private String postUserId;
    private String commentedUserId;
}
