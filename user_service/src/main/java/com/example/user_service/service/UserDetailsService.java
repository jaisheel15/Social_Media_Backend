package com.example.user_service.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Cacheable(value = "users", key = "#userId")
    public UserDetail getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Cacheable(value = "usersByAuthId", key = "#authUserId")
    public UserDetail getUserByAuthUserId(String authUserId) {
        return userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new RuntimeException("User profile not found. Please create a profile first."));
    }

    @Cacheable(value = "userEmails", key = "#userId")
    public String getUserByEmail(String userId) {
        UserDetail user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }

    @CacheEvict(value = "usersByAuthId", key = "#authUserId")
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

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#id"),
            @CacheEvict(value = "userEmails", key = "#id")
    })
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

    @Caching(evict = {
            @CacheEvict(value = "followers", key = "#targetUserId"),
            @CacheEvict(value = "following", key = "#authUserId"),
            @CacheEvict(value = "followersCount", key = "#targetUserId"),
            @CacheEvict(value = "followingCount", key = "#authUserId")
    })
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

    // Note: Caching disabled for Page objects due to Redis deserialization
    // complexity
    public Page<UserDetail> getFollowers(String userId, Pageable pageable) {
        List<Follow> follows = followRepository.findByFollowingId(userId);
        List<UserDetail> allFollowers = follows.stream()
                .map(follow -> userRepository.findById(follow.getFollowerId()).orElse(null))
                .filter(user -> user != null)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFollowers.size());

        List<UserDetail> pageContent = start < allFollowers.size() ? allFollowers.subList(start, end) : List.of();

        return new PageImpl<>(pageContent, pageable, allFollowers.size());
    }

    // Note: Caching disabled for Page objects due to Redis deserialization
    // complexity
    public Page<UserDetail> getFollowing(String userId, Pageable pageable) {
        List<Follow> follows = followRepository.findByFollowerId(userId);
        List<UserDetail> allFollowing = follows.stream()
                .map(follow -> userRepository.findById(follow.getFollowingId()).orElse(null))
                .filter(user -> user != null)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFollowing.size());

        List<UserDetail> pageContent = start < allFollowing.size() ? allFollowing.subList(start, end) : List.of();

        return new PageImpl<>(pageContent, pageable, allFollowing.size());
    }

    @Cacheable(value = "followersCount", key = "#userId")
    public long getFollowersCount(String userId) {
        return followRepository.countByFollowingId(userId);
    }

    @Cacheable(value = "followingCount", key = "#userId")
    public long getFollowingCount(String userId) {
        return followRepository.countByFollowerId(userId);
    }

}
