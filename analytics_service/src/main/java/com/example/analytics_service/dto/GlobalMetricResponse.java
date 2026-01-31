package com.example.analytics_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalMetricResponse {
    private long totalUsers;
    private long totalPosts;
    private long totalLikes;
    private long totalFollows;
}
