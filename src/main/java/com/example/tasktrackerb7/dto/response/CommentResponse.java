package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CommentResponse {

    private Long id;
    private String text;
    private LocalDateTime localDateTime;
    private Boolean isMine;
    private UserResponse userResponse;
    @JsonIgnore
    private User user;

    public CommentResponse(Long id, String text, LocalDateTime localDateTime, Boolean isMine, UserResponse userResponse, User user) {
        this.id = id;
        this.text = text;
        this.localDateTime = localDateTime;
        this.isMine = isMine;
        this.userResponse = userResponse;
        this.user = user;
    }
}
