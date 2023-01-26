package com.example.tasktrackerb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChecklistRequest {

    private String name;

    private List<ItemRequest> itemRequests;
}
