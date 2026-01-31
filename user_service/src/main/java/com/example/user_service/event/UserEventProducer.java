package com.example.user_service.event;

import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import com.example.user_service.model.UserCreatedEvent;
import com.example.user_service.model.UserFollowEvent;
import com.example.user_service.topic.UserTopic;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserEventProducer {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    //Used in Notfication_Service and Analytics_Service
        public void publishFollowEvent(String followerId, String followingId, String email) {
        UserFollowEvent event = new UserFollowEvent(followerId, followingId , email);

                kafkaTemplate.send(
                UserTopic.USER_FOLLOW_EVENTS.name(),
                followingId,
                event
        );
    }   

    //Used in Analytics_Service and notification_service
         public void publishUserCreatedEvent(String userid, String email , Instant createdAt){
                UserCreatedEvent event = new UserCreatedEvent(userid, email, createdAt);

                kafkaTemplate.send(
                UserTopic.USER_CREATED_EVENTS.name(),
                userid,
                event
        );
}

    }
