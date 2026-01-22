package com.example.post_service.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private String title;
    private List<UUID> media;
    private String content;
    private String authorId;
}
