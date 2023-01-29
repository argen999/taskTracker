package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardTitleRequest {

    private Long id;

    private String title;
}
