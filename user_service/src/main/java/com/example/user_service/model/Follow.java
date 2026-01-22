package com.example.user_service.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user_follows")
@CompoundIndex(
    name = "user_follow_idx",
    def = "{'followerId': 1, 'followingId': 1}",
    unique = true
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    private String id;

    private String followerId;

    private String followingId;

    private Instant createdAt = Instant.now();


    public Follow(String followerId, String followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
