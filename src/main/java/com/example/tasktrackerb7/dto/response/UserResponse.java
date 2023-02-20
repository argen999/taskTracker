package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String fullName;
    private String photoLink;

    public UserResponse(Long userId, String fullName, String photoLink) {
        this.userId = userId;
        this.fullName = fullName;
        this.photoLink = photoLink;
    }
}
