package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardRequest;
import com.example.tasktrackerb7.dto.response.BoardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.CardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.CardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CardService {

    CardInnerPageResponse create(CardRequest cardRequest);

    CardInnerPageResponse update(UpdateCardRequest request);

    CardInnerPageResponse getById(Long id);

    SimpleResponse delete(Long id);

    BoardInnerPageResponse getAll(Long id);

    SimpleResponse archive(Long id);

    List<CardResponse> getAllArchivedCards(Long id);

    SimpleResponse inviteToCard(Long id, Long cardId);
}
