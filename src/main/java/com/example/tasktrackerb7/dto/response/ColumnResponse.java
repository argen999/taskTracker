package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ColumnResponse {
    private Long id;

    private String name;

    public ColumnResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
