package com.example.media_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.media_service.model.Media;
import com.example.media_service.service.MediaService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    // @PostMapping("/upload")
    // public ResponseEntity<Media> Upload(@RequestParam("file") MultipartFile file, @RequestHeader("X-User-Id") String userId) throws Exception {
    //     Media media = mediaService.upload(file, userId);
        
    //     return ResponseEntity.ok(media);
    // }

    @GetMapping("/user/")
    public ResponseEntity<List<Media>> GetByUser(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(mediaService.getByUserId(userId));
    }

        @GetMapping("/{id}")
    public ResponseEntity<Media> GetById(@PathVariable UUID id) {
        return ResponseEntity.ok(mediaService.getById(id));
    }
    
    

}
