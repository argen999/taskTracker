package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardTitleRequest;
import com.example.tasktrackerb7.dto.response.CardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.CardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {

    CardInnerPageResponse create(CardRequest cardRequest);

    CardInnerPageResponse updateTitle(UpdateCardTitleRequest request);

    CardInnerPageResponse getById(Long id);

    List<CardResponse> getAllCardsByColumnId(Long id);

    SimpleResponse delete(Long id);
}
