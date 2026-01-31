package com.example.media_service.service;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.media_service.config.MinioProperties;
import com.example.media_service.model.Media;
import com.example.media_service.repository.MediaRepository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MinioClient minioClient;
    private final MinioProperties props;
    private final MediaRepository mediaRepository;

    @CacheEvict(value = "userMedia", key = "#userId")
    public Media upload(MultipartFile file, String userId) throws Exception {
        String objectName = "media/" + userId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(props.getBucket())
                        .build());

        if (!bucketExists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(props.getBucket())
                            .build());
        }
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(props.getBucket())
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        Media media = Media.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .mediaType(file.getContentType().startsWith("image") ? "IMAGE" : "VIDEO")
                .mimeType(file.getContentType())
                .originalName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .storagePath(objectName)
                .status("READY")
                .build();

        return mediaRepository.save(media);
    }

    @Cacheable(value = "media", key = "#id")
    public Media getById(UUID id) {
        return mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found"));
    }

    // Note: Caching disabled for Page objects due to Redis deserialization
    // complexity
    public Page<Media> getByUserId(String userId, Pageable pageable) {
        return mediaRepository.findByUserId(userId, pageable);
    }

}
