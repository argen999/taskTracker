package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EstimationRequest {

    private LocalDate dateOfStart;

    private LocalDate datOfFinish;
}
