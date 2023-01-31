package com.example.tasktrackerb7.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FavouriteResponse {

    private Long id;

    private String workspaceName;

    private String boardName;

    private String photoLinkBoard;

    public FavouriteResponse(Long id, String workspaceName, String boardName, String photoLinkBoard) {
        this.id = id;
        this.workspaceName = workspaceName;
        this.boardName = boardName;
        this.photoLinkBoard = photoLinkBoard;
    }
}
