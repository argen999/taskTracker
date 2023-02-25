package com.example.tasktrackerb7.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FavouriteBoardResponse {

    private Long id;

    private  Long boardId;

    private String boardName;

    private String photoLinkBoard;

}
