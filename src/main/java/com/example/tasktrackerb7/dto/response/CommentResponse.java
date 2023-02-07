package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String text;

    private LocalDateTime localDateTime;

    private String userName;


    private String photoLinkUser;

    public CommentResponse(Long id, String text, String firstNameUser,String photoLinkUser, LocalDateTime localDateTime) {
        this.id = id;
        this.text = text;
        this.userName = firstNameUser;
        this.photoLinkUser = photoLinkUser;
        this.localDateTime = localDateTime;
    }
}
