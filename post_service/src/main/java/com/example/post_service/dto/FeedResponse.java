package com.example.post_service.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedResponse {
    private String postId;
    private String userId;
    private String title;
    private String content;
    private List<UUID> media;
    private int likeCount;
    private List<String> comment;

}
