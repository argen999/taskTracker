package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
public class ProfileResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String newToken;

    private String photoLink;

    public ProfileResponse(Long id, String name, String surname, String email, String photoLink) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoLink = photoLink;
    }
}
