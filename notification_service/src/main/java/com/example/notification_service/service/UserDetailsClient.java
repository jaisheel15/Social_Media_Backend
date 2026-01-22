package com.example.notification_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsClient {
    private final RestTemplate restTemplate;

    public String getUserEmailById(String userId) {
        String url = "http://user-service:8081/internal/users/" + userId + "/email";
        return restTemplate.getForObject(url, String.class);
    }

}
