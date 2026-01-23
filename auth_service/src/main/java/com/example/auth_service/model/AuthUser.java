package com.example.auth_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "auth_users")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthUser {

    @Id
    private String id;

    private String email;

    private String password;


    private Role role;

}
