package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateRequest {

    private Long boardId;

    private String value;
}
