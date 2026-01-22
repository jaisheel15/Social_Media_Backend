package com.example.notification_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification_service.service.EmailService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;


    @PostMapping("/send")
    public String sendEmail() {
        emailService.sendEmail("sainandhan2006@gmail.com", "test", "hello this is test email from notification service");
        return "Email sent successfully";
    }
    
}
