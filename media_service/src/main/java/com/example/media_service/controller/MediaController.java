package com.example.media_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.media_service.model.Media;
import com.example.media_service.service.MediaService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<Media> Upload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) throws Exception {
        String userId = (String) authentication.getPrincipal();
        Media media = mediaService.upload(file, userId);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/user/")
    public ResponseEntity<Page<Media>> GetByUser(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "uploadedAt") Pageable pageable) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(mediaService.getByUserId(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> GetById(@PathVariable UUID id) {
        return ResponseEntity.ok(mediaService.getById(id));
    }

}
