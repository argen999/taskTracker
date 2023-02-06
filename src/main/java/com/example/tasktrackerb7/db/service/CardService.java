package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardRequest;
import com.example.tasktrackerb7.dto.response.BoardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.CardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;


@Service
public interface CardService {

    CardInnerPageResponse create(CardRequest cardRequest);

    CardInnerPageResponse update(UpdateCardRequest request);

    CardInnerPageResponse getById(Long id);

    SimpleResponse delete(Long id);

    BoardInnerPageResponse getAllCards(Long id);
}
