package com.example.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.example.notification_service.dto.UserFollowedEvent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FollowNotificationListener {
    private final EmailService emailService;

    @KafkaListener(topics = "USER_FOLLOW_EVENTS", groupId = "notification-service-group")
    public void onUserFollow(UserFollowedEvent event) {
        try {
            String recipientEmail = event.getEmail();

            if (recipientEmail == null || recipientEmail.isBlank()) {
                log.warn("Skipping notification email because no recipient address was found for user {}",
                        event.getFollowingId());
                return;
            }

            emailService.sendEmail(
                    recipientEmail,
                    "You have a new follower!",
                    "User with ID " + event.getFollowerId() + " has started following you.");
        } catch (RestClientException ex) {
            log.error("Failed to retrieve recipient email for user {}", event.getFollowingId(), ex);
        }
    }

    

}
