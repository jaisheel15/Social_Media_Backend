package com.example.notification_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.notification_service.model.UserInfo;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
    UserInfo findByUserId(String userId);

}
