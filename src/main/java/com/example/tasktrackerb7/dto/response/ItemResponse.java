package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemResponse {

    private Long id;

    private String description;

    private boolean isDone;

//    private List<MemberResponse> memberResponses;

//    private EstimationResponse estimationResponse;

    public ItemResponse(Long id, String description, boolean isDone) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
    }
}
