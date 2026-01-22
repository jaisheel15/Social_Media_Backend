package com.example.user_service.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user_service.model.Follow;

public interface FollowRepository extends MongoRepository<Follow, String> {
    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

    List<Follow> findByFollowerId(String followerId);

    List<Follow> findByFollowingId(String followingId);

    long countByFollowerId(String followerId);

    long countByFollowingId(String followingId);
}
