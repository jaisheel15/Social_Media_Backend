package com.example.auth_service.service;

import org.springframework.stereotype.Service;

import com.example.auth_service.model.AuthUser;
import com.example.auth_service.repository.AuthUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;


    public boolean userExists(String email) {
        return authUserRepository.existsByEmail(email);
    }

    public AuthUser getUserByUsername(String email) {
        return authUserRepository.findByEmail(email).orElse(null);
    }
}
