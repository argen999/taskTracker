package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentResponse {

    private Long id;

    private String attachment;

    private LocalDate dateOfStart;

}
