package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.threeten.bp.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequest {

    private Long id;

    @NotNull(message = "Comment cannot be empty!")
    @NotBlank(message = "Name cannot be empty!")
    private String text;

    private LocalDateTime localDateTime;

}
