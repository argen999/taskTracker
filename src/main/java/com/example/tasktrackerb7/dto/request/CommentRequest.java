package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentRequest {

   @NotNull(message = "Comment cannot be empty!")
   @NotBlank(message = "Name cannot be empty!")
   private String text;
}
