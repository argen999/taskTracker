package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FavouriteResponse {

    private List<FavouriteBoardResponse> board;

    private List<FavouriteWorkspaceResponse> workspace;
}
