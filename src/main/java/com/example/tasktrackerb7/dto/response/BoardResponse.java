package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Favourite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    private Long id;

    private String name;

    private String background;

    private boolean isFavourite;

}
