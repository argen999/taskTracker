package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthWithGoogleResponse {

    private Long id;

    private String email;

    private String role;

    private String token;

    public AuthWithGoogleResponse(Long id, String email, String role, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
