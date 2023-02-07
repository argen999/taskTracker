package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse {

    private Long id;

    private String description;

    private boolean isDone;

    public ItemResponse(Long id, String description, boolean isDone) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
    }
}
