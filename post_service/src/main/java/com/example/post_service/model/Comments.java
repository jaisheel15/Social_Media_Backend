package com.example.post_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "comments")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Comments {
    @Id
    private String id;

    private String postId;

    private String userId;

    private String content;
}
