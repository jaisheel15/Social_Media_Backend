package com.example.analytics_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "global_metrics")
public class GlobalMetrics {
    @Id
    private String id;
    private long totalUsers = 0;
    private long totalPosts = 0;
    private long totalFollows = 0;
    private long totalLikes = 0;

}
