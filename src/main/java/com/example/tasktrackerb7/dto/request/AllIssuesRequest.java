package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AllIssuesRequest {

    private LocalDate from;

    private LocalDate to;

    private Long id;//memberId or labelId

    private Boolean memberOrLabel;

}
