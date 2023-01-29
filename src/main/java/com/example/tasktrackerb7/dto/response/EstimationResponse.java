package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimationResponse {

    private Long id;

    private LocalDate dateOfWrite;

    private LocalDate dateOfFinish;
}
