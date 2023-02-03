package com.example.tasktrackerb7.dto.request;

import com.example.tasktrackerb7.validations.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ProfileRequest {

    @NotNull(message = "User cannot be unnamed!")
    @NotBlank(message = "User name cannot be empty!")
    @Size(min = 3, message = "User name cannot be too short!")
    @Size(max = 15, message = "User name cannot be too long!")
    private String name;

    @NotNull(message = "User cannot be unnamed!")
    @NotBlank(message = "User surname cannot be empty!")
    @Size(min = 3, message = "User surname cannot be too short!")
    @Size(max = 15, message = "User surname cannot be too long!")
    private String surname;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
//    @Password
    private String password;

    private String photoLink;
}
