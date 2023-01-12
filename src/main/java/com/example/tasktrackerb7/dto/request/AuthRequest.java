package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthRequest {

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
