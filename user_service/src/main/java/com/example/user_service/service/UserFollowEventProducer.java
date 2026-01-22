package com.example.user_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.user_service.model.UserFollowEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFollowEventProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

        private static final String TOPIC = "user-follow-events";

    public void publishFollowEvent(String followerId, String followingId) {
        UserFollowEvent event = new UserFollowEvent(followerId, followingId);

                kafkaTemplate.send(
                TOPIC,
                followingId.toString(),
                event
        );
    }

}
