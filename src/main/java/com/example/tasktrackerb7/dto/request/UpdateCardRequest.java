package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardRequest {

    private Long id;

    private String value;

    private boolean isName;
}
