package com.example.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.CreateUserRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.event.UserEventProducer;
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
    private final UserEventProducer userEventProducer;

    public UserDetail getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetail getUserByAuthUserId(String authUserId) {
        return userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new RuntimeException("User profile not found. Please create a profile first."));
    }

    public String getUserByEmail(String userId) {
        UserDetail user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }

    public UserDetail createUserRequest(String authUserId, CreateUserRequest request) {
        // Check if user profile already exists for this auth user
        UserDetail existingUser = userRepository.findByAuthUserId(authUserId).orElse(null);
        if (existingUser != null) {
            throw new RuntimeException("User profile already exists for this account");
        }

        UserDetail userDetail = new UserDetail();
        userDetail.setAuthUserId(authUserId);
        userDetail.setUsername(request.getUsername());
        userDetail.setEmail(request.getEmail());
        userDetail.setAvatarUrl(request.getAvatarUrl());
        userDetail.setBio(request.getBio());
        UserDetail savedUser = userRepository.save(userDetail);
        userEventProducer.publishUserCreatedEvent(savedUser.getId(), savedUser.getEmail(), savedUser.getCreatedAt());
        return savedUser;
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

            UserDetail savedUser = userRepository.save(userDetail);
            userEventProducer.publishUserCreatedEvent(savedUser.getId(), savedUser.getEmail(),
                    savedUser.getCreatedAt());
            return savedUser;
        }
        return null;
    }

    public String followUser(String authUserId, String targetUserId) {
        // Get the follower's user profile using authUserId
        UserDetail follower = userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new RuntimeException("User profile not found. Please create a profile first."));

        // Get the target user profile using their MongoDB ID
        UserDetail targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        String followerId = follower.getId();

        log.info("Follower ID: {}", followerId);
        log.info("Target User ID: {}", targetUserId);

        if (followerId.equals(targetUserId)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        if (!followRepository.existsByFollowerIdAndFollowingId(followerId, targetUserId)) {
            followRepository.save(new Follow(followerId, targetUserId));

            follower.setFollowingCount(follower.getFollowingCount() + 1);
            userRepository.save(follower);

            targetUser.setFollowersCount(targetUser.getFollowersCount() + 1);
            userRepository.save(targetUser);

            userEventProducer.publishFollowEvent(followerId, targetUserId, targetUser.getEmail());
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
