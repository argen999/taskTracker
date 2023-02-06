package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@RequiredArgsConstructor
public class ColumnResponse {

    private Long id;

    private String name;

    private List<CardResponse> cardResponses;

    public ColumnResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
