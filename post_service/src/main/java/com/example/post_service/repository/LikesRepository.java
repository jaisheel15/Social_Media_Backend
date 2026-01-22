package com.example.post_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.post_service.model.Likes;

public interface LikesRepository extends MongoRepository<Likes, String> {
    Optional<Likes> findByUserIdAndPostId(String userId, String postId);
}
