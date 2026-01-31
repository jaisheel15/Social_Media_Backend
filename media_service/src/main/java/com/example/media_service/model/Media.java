package com.example.media_service.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "media")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Media {

    @Id
    private UUID id;

    private String userId;

    private String mediaType;

    private String mimeType;

    private String originalName;

    private long fileSize;

    private String storagePath;

    private String status;

    @Builder.Default
    private Instant uploadedAt = Instant.now();
}
