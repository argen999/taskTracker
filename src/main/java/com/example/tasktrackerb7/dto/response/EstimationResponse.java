package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class EstimationResponse {

    private Long id;

    private LocalDateTime dateOfStart;

    private LocalDateTime dateOfFinish;

}
