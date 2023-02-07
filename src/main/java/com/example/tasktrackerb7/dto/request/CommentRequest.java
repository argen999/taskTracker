package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentRequest {

<<<<<<< HEAD

   @NotNull(message = "Comment cannot be empty!")
   @NotBlank(message = "Name cannot be empty!")
   private String text;

=======
    private String text;
>>>>>>> f63e53f9c710477dc8c2853e3bf1177c5b4b39d2
}
