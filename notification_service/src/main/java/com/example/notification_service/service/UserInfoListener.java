package com.example.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.notification_service.dto.UserCreatedEvent;
import com.example.notification_service.model.UserInfo;
import com.example.notification_service.repository.UserInfoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserInfoListener {
    private final UserInfoRepository userInfoRepository;

    @KafkaListener(topics = "USER_CREATED_EVENTS", groupId = "notification-service-group")
    public void setUserInfo(UserCreatedEvent event) {
        UserInfo userInfo = new UserInfo(event.getUserId(), event.getEmail());
        log.info("UserInfo : " , userInfo);
        userInfoRepository.save(userInfo);
    }


}
