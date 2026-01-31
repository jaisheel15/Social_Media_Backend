package com.example.post_service.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "posts")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Post {
    @Id
    private String id;

    private String title;

    private List<UUID> media;

    private String content;

    private String authorId;

    private List<String> commentsIds;

    private List<String> likesIds;

    private int likeCount;

    @Builder.Default
    private Instant createdAt = Instant.now();

}
