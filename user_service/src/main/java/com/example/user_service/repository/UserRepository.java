package com.example.user_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user_service.model.UserDetail;

public interface UserRepository extends MongoRepository<UserDetail, String> {
    Optional<UserDetail> findByAuthUserId(String authUserId);
}
