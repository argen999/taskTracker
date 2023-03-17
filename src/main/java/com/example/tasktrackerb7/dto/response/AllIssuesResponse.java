package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllIssuesResponse {

    private Long id;

    private LocalDate created;

    private int period;

    private Long creatorId;

    private String creatorName;

    private String creatorSurname;

    private String columnName;

    private List<CardMemberResponse> cardMemberResponses;

    private List<LabelResponse> labelResponses;

    private String checklist;

    private String description;

    public AllIssuesResponse(Card card) {
        this.id = card.getId();
        this.created = card.getCreated();
        this.creatorId = card.getCreator().getId();
        this.creatorName = card.getCreator().getName();
        this.creatorSurname = card.getCreator().getSurname();
        this.columnName = card.getColumn().getName();
        this.description = card.getDescription();
    }
}
