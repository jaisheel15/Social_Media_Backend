package com.example.post_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.post_service.model.PostCommentEvent;
import com.example.post_service.model.PostCreatedEvent;
import com.example.post_service.model.PostLikeEvent;
import com.example.post_service.topic.PostTopic;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostEventProducer {

    KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPostCreateEvent(String postUserId, String postId ) {
        // Construct the PostCreatedEvent (assuming such a class exists)
        PostCreatedEvent event = new PostCreatedEvent(postUserId, postId);

        kafkaTemplate.send(
                PostTopic.POST_CREATED_EVENTS.name(), 
                postId,
                event
        );
    }

    public void publishPostLikeEvent(String postId, String postUserId, String likedUserId) {
        // Construct the PostLikedEvent (assuming such a class exists)
        PostLikeEvent event = new PostLikeEvent(postId, postUserId, likedUserId);

        kafkaTemplate.send(
                PostTopic.POST_LIKED_EVENTS.name(),
                postId,
                event
        );
    }

    public void publishPostCommentEvent(String postId, String postUserId, String commentedUserId) {
        // Construct the PostCommentedEvent (assuming such a class exists)
        PostCommentEvent event = new PostCommentEvent(postId, postUserId, commentedUserId);

        kafkaTemplate.send(
                PostTopic.POST_COMMENTED_EVENTS.name(),
                postId,
                event
        );
    }


}
