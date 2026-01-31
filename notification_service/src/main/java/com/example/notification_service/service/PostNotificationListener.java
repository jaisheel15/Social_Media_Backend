package com.example.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.notification_service.model.PostCommentedEvent;
import com.example.notification_service.model.PostLikedEvent;
import com.example.notification_service.repository.UserInfoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PostNotificationListener {

    private final EmailService emailService;
    private final UserInfoRepository userInfoRepository;

    @KafkaListener(topics = "POST_LIKED_EVENTS", groupId = "notification-service-group")
    public void onUserLikedPost(PostLikedEvent event) {
        try {
            log.info("Received POST_LIKED_EVENTS: postId={}, postUserId={}, likedUserId={}",
                    event.getPostId(), event.getPostUserId(), event.getLikedUserId());

            var userInfo = userInfoRepository.findByUserId(event.getPostUserId());

            if (userInfo == null) {
                log.warn("Skipping notification email because user info not found for user {}",
                        event.getPostUserId());
                return;
            }

            String recipientEmail = userInfo.getEmail();

            if (recipientEmail == null || recipientEmail.isBlank()) {
                log.warn("Skipping notification email because no recipient address was found for user {}",
                        event.getPostUserId());
                return;
            }

            emailService.sendEmail(
                    recipientEmail,
                    "Your post was liked!",
                    "User with ID " + event.getLikedUserId() + " has liked your post.");

            log.info("Successfully sent like notification email to {}", recipientEmail);
        } catch (Exception ex) {
            log.error("Failed to send like notification email for postUserId {}", event.getPostUserId(), ex);
        }
    }

    @KafkaListener(topics = "POST_COMMENTED_EVENTS", groupId = "notification-service-group")
    public void onUserCommentedPost(PostCommentedEvent event) {
        try {
            log.info("Received POST_COMMENTED_EVENTS: postId={}, postUserId={}, commentedUserId={}",
                    event.getPostId(), event.getPostUserId(), event.getCommentedUserId());

            var userInfo = userInfoRepository.findByUserId(event.getPostUserId());

            if (userInfo == null) {
                log.warn("Skipping notification email because user info not found for user {}",
                        event.getPostUserId());
                return;
            }

            String recipientEmail = userInfo.getEmail();

            if (recipientEmail == null || recipientEmail.isBlank()) {
                log.warn("Skipping notification email because no recipient address was found for user {}",
                        event.getPostUserId());
                return;
            }

            emailService.sendEmail(
                    recipientEmail,
                    "Your post was commented on!",
                    "User with ID " + event.getCommentedUserId() + " has commented on your post.");

            log.info("Successfully sent comment notification email to {}", recipientEmail);
        } catch (Exception ex) {
            log.error("Failed to send comment notification email for postUserId {}", event.getPostUserId(), ex);
        }
    }
}
