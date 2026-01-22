package com.example.post_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommentRequest {
    private String postId;

    private String userId;

    private String content;
}
