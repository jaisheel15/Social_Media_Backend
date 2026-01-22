package com.example.user_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.service.UserDetailsService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@RestController
@RequestMapping("/internal/users")
public class InternalController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/{id}/email")
    public String GetUserEmail(@PathVariable String id) {
        return userDetailsService.getUserByEmail(id);
    }
    
}
