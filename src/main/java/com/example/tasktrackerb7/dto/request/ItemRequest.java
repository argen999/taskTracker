package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemRequest {

    private String text;

    private Boolean isDone;

}