package com.example.tasktrackerb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistResponse {

    private Long id;

    private String name;

    private int countOfCompletedItems;

    private int countOfItems;

    private int percent;

    private List<ItemResponse> itemResponses;

}
