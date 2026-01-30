package com.example.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.CreateUserRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.model.Follow;
import com.example.user_service.model.UserDetail;
import com.example.user_service.repository.FollowRepository;
import com.example.user_service.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserFollowEventProducer userFollowEventProducer;

    public UserDetail getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String getUserByEmail(String userId) {
        UserDetail user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }

    public UserDetail createUserRequest(CreateUserRequest request) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(request.getUsername());
        userDetail.setEmail(request.getEmail());
        userDetail.setAvatarUrl(request.getAvatarUrl());
        userDetail.setBio(request.getBio());
        return userRepository.save(userDetail);
    }

    public UserDetail updateUserDetails(String id, UserUpdateRequest request) {
        UserDetail userDetail = userRepository.findById(id).orElse(null);
        if (userDetail != null) {
            if (request.getBio() != null)
                userDetail.setBio(request.getBio());
            if (request.getUsername() != null)
                userDetail.setUsername(request.getUsername());
            if (request.getAvatarUrl() != null)
                userDetail.setAvatarUrl(request.getAvatarUrl());
            if (request.getEmail() != null)
                userDetail.setEmail(request.getEmail());

            return userRepository.save(userDetail);
        }
        return null;
    }

    public String followUser(String userId, String targetUserId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserDetail targetUser = userRepository.findById(targetUserId)
                .orElse(null);
        log.info(userId);
        log.info(targetUserId);
        if (targetUser == null) {
            return "Target user not found";
        }
        log.info(targetUser.getUsername());
        if (userId.equals(targetUserId)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        if (!followRepository.existsByFollowerIdAndFollowingId(userId, targetUserId)) {
            followRepository.save(new Follow(userId, targetUserId));

            userRepository.findById(userId).ifPresent(follower -> {
                follower.setFollowingCount(follower.getFollowingCount() + 1);
                userRepository.save(follower);
            });

            userRepository.findById(targetUserId).ifPresent(following -> {
                following.setFollowersCount(following.getFollowersCount() + 1);
                userRepository.save(following);
            });
            
            userFollowEventProducer.publishFollowEvent(userId, targetUserId , targetUser.getEmail());
            return "Successfully followed the user";
        }

        return "You are already following this user";
    }

    public List<UserDetail> getFollowers(String userId) {
        List<Follow> follows = followRepository.findByFollowingId(userId);
        return follows.stream().map(follow -> userRepository.findById(follow.getFollowerId()).orElse(null)).toList();
    }

    public List<UserDetail> getFollowing(String userId) {
        List<Follow> follows = followRepository.findByFollowerId(userId);
        return follows.stream().map(follow -> userRepository.findById(follow.getFollowingId()).orElse(null)).toList();
    }

    public long getFollowersCount(String userId) {
        return followRepository.countByFollowingId(userId);
    }

    public long getFollowingCount(String userId) {
        return followRepository.countByFollowerId(userId);
    }

}
