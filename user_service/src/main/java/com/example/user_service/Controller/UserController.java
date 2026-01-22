package com.example.user_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.CreateUserRequest;
import com.example.user_service.dto.FollowUserRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.model.UserDetail;
import com.example.user_service.service.UserDetailsService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

    @PostMapping("/")
    public ResponseEntity<UserDetail> CreateUser(@RequestBody CreateUserRequest entity) {
        return ResponseEntity.ok(userDetailsService.createUserRequest(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetail> putMethodName(@PathVariable String id, @RequestBody UserUpdateRequest entity) {
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id , entity));
    }

    @PostMapping("/{userId}/follow/")
    public ResponseEntity<String> FollowUser(@PathVariable String userId, @RequestBody FollowUserRequest request) {
            return ResponseEntity.ok(userDetailsService.followUser(userId , request.getTargetUserId()));
    }

    @GetMapping("/{userId}/followers/")
    public ResponseEntity<List<UserDetail>> GetFollowerUsers(@PathVariable String userId) {
        return ResponseEntity.ok(userDetailsService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following/")
    public ResponseEntity<List<UserDetail>> GetFollowingUsers(@PathVariable String userId) {
        return ResponseEntity.ok(userDetailsService.getFollowing(userId));
    }
    
    
}
