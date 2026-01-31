package com.example.media_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.media_service.model.Media;

public interface MediaRepository extends MongoRepository<Media, UUID> {
    List<Media> findByUserId(String userId);

    Page<Media> findByUserId(String userId, Pageable pageable);
}
