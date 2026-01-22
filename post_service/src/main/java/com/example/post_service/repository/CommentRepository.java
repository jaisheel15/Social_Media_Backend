package com.example.post_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.post_service.model.Comments;

public interface CommentRepository extends MongoRepository<Comments,String>{

} 
