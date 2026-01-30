package com.example.auth_service.config;


import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String , Bucket> cache = new ConcurrentHashMap<>();

    private Bucket newBucket(){
        return Bucket.builder()
        .addLimit(Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1))
        )).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                String key = (auth !=null) ? auth.getPrincipal().toString(): request.getRemoteAddr();

                Bucket bucket = cache.computeIfAbsent(key, k -> newBucket());

                if(bucket.tryConsume(1)){
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(429);
                    response.getWriter().write("Too many requests");
                }
    }
}
