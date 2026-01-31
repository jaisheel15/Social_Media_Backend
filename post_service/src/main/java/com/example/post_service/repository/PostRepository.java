package com.example.post_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.post_service.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthorId(String authorId, Pageable pageable);
}
