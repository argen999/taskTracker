package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

    private String text;

    private boolean isDone;

}
