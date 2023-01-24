package com.example.tasktrackerb7.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ColumnRequest {

    @NotNull(message = "Column cannot be unnamed!")
    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 3, message = "Column name cannot be too short!")
    @Size(max = 30, message = "Column name cannot be too long!")
    private String name;
}
