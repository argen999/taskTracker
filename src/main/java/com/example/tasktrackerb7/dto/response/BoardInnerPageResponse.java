package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardInnerPageResponse {

    private String boardName;

    private List<ColumnResponse> columnResponses;
}
