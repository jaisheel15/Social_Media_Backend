package com.example.post_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.post_service.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
