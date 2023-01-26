package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardRequest {

    private Long columnId;

    private String name;

    private String description;

    private List<LabelRequest> labels;

    private EstimationRequest estimationRequest;

    private List<MemberRequest> memberRequests;

    private List<ChecklistRequest> checklistRequests;

    private List<CommentRequest> commentRequests;
}
