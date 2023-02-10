package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Long id;
    private String text;
    private String name;
    private String surname;
    private String photoLink;
    private LocalDateTime localDateTime;

    public CommentResponse(Long id, String text, String name, String surname, LocalDateTime localDateTime, String photoLink) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.surname = surname;
        this.localDateTime = localDateTime;
        this.photoLink = photoLink;
    }
}
