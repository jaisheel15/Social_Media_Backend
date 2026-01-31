package com.example.analytics_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.analytics_service.dto.GlobalMetricResponse;
import com.example.analytics_service.service.GlobalAnalyticsService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final GlobalAnalyticsService userAnalyticsService;

    @GetMapping("/overview")
    public ResponseEntity<GlobalMetricResponse> GetOverView() {
        return ResponseEntity.ok(userAnalyticsService.getGlobalMetrics());
    }
    


}
