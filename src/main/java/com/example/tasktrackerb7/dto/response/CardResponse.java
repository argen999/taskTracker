package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardResponse {

    private Long id;

    private String name;

    private List<LabelResponse> labelResponses;

    private String duration;

    private int numOfMembers;

    private int numOfItems;

    private int numOfCompletedItems;

    private List<CommentResponse> commentResponses;

}
