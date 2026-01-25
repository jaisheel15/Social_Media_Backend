package com.example.post_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.post_service.dto.CreateCommentRequest;
import com.example.post_service.dto.CreatePostRequest;
import com.example.post_service.dto.FeedRequest;
import com.example.post_service.dto.FeedResponse;
import com.example.post_service.dto.UserLikeRequest;
import com.example.post_service.model.Likes;
import com.example.post_service.model.Post;
import com.example.post_service.service.PostService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPost(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Post> CreatePost(@RequestBody CreatePostRequest entity) {
        return ResponseEntity.ok(postService.createPost(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post Deleted");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Likes> LikeOnPost(@PathVariable String id, @RequestBody UserLikeRequest entity) {
        
        return ResponseEntity.ok(postService.likePost(entity.getUserId(), id));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> CommentOnPost(@PathVariable String id, @RequestBody CreateCommentRequest entity) {
        postService.commentPost(id, entity);
        return ResponseEntity.ok("Comment Added");
    }

    @GetMapping("/feed")
    public ResponseEntity<List<FeedResponse>> GetFeed(@RequestBody FeedRequest user) {
        return ResponseEntity.ok(postService.getFeed(user.getUserId()));
    }

}
