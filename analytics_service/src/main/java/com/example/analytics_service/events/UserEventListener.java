package com.example.analytics_service.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.analytics_service.model.PostCreatedEvent;
import com.example.analytics_service.model.PostLikeEvent;
import com.example.analytics_service.model.UserCreatedEvent;
import com.example.analytics_service.model.UserFollowEvent;
import com.example.analytics_service.service.GlobalAnalyticsService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserEventListener {
    private final GlobalAnalyticsService globalAnalyticsService;

    @KafkaListener(topics = "USER_CREATED_EVENTS", groupId = "analytics_service_group")
    public void createUserEventListener(UserCreatedEvent event) {
        log.info("Received User Created Event: {}", event);
        globalAnalyticsService.incrementUserCreatedCount();
    }

    @KafkaListener(topics = "USER_FOLLOW_EVENTS", groupId = "analytics_service_group")
    public void followUserEventListener(UserFollowEvent event) {
        log.info("Received User Follow Event: {}", event);
        globalAnalyticsService.incrementFollowCount();
    }

    @KafkaListener(topics = "POST_LIKED_EVENTS", groupId = "analytics_service_group")
    public void likeUserEventListener(PostLikeEvent event) {
        log.info("Received Post Like Event: {}", event);
        globalAnalyticsService.incrementLikeCount();
    }

    @KafkaListener(topics = "POST_CREATED_EVENTS", groupId = "analytics_service_group")
    public void postUserEventListener(PostCreatedEvent event) {
        log.info("Received Post Created Event: {}", event);
        globalAnalyticsService.incrementPostsCount();
    }

}
