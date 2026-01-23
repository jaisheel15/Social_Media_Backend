package com.example.auth_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.auth_service.model.AuthUser;


public interface AuthUserRepository extends MongoRepository<AuthUser, String> {
    Optional<AuthUser>  findByEmail(String email);

    boolean existsByEmail(String email);
}
