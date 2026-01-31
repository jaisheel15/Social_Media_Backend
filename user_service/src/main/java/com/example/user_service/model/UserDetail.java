package com.example.user_service.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_details")
public class UserDetail {
    @Id
    private String id;

    private String username;

    private String email;

    private String authUserId; // Links to auth service user ID

    private String avatarUrl;

    private String bio;

    private long followersCount;

    private long followingCount;

    private Instant createdAt = Instant.now();

}
