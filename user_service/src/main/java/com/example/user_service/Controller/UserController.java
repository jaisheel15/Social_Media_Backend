package com.example.user_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.CreateUserRequest;
import jakarta.validation.Valid;
import com.example.user_service.dto.FollowUserRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.model.UserDetail;
import com.example.user_service.service.UserDetailsService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetail> GetUser(@PathVariable String userId) {
        return ResponseEntity.ok(userDetailsService.getUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetail> GetCurrentUser(Authentication authentication) {
        String authUserId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.getUserByAuthUserId(authUserId));
    }

    @PostMapping("/")
    public ResponseEntity<UserDetail> CreateUser(Authentication authentication,
            @Valid @RequestBody CreateUserRequest entity) {
        String authUserId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.createUserRequest(authUserId, entity));
    }

    @PutMapping("/")
    public ResponseEntity<UserDetail> UpdateUser(Authentication authentication,
            @Valid @RequestBody UserUpdateRequest entity) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.updateUserDetails(userId, entity));
    }

    @PostMapping("/follow")
    public ResponseEntity<String> FollowUser(Authentication authentication,
            @Valid @RequestBody FollowUserRequest request) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.followUser(userId, request.getTargetUserId()));
    }

    @GetMapping("/followers")
    public ResponseEntity<Page<UserDetail>> GetFollowerUsers(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.getFollowers(userId, pageable));
    }

    @GetMapping("/following")
    public ResponseEntity<Page<UserDetail>> GetFollowingUsers(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userDetailsService.getFollowing(userId, pageable));
    }

}
