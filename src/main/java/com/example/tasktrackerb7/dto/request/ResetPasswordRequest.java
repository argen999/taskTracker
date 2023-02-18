package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotNull
    @NotBlank
    private  String newPassword;
}
