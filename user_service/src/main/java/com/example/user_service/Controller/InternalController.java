package com.example.user_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.service.UserDetailsService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@AllArgsConstructor
@RestController
@RequestMapping("/internal/users")
public class InternalController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/email")
    public String GetUserEmail( @RequestHeader("X-User-Id") String userId) {
        return userDetailsService.getUserByEmail(userId);
    }
    
}
