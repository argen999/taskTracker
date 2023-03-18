package com.example.tasktrackerb7.dto.response;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteBoardResponse {

    private Long id;

    private  Long boardId;

    private String boardName;

    private String photoLinkBoard;

    public FavouriteBoardResponse(Long id, Long boardId, String boardName, String photoLinkBoard) {
        this.id = id;
        this.boardId = boardId;
        this.boardName = boardName;
        this.photoLinkBoard = photoLinkBoard;
    }
}
