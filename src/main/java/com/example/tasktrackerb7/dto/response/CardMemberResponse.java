package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardMemberResponse {

    private Long id;

    private String photoLink;

    public CardMemberResponse(User user) {
        this.id = user.getId();
        this.photoLink = user.getPhotoLink();
    }
}
