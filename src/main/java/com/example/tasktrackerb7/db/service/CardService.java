package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.response.CardInnerPageResponse;
import org.springframework.stereotype.Service;

@Service
public interface CardService {

    CardInnerPageResponse create(CardRequest cardRequest);
}
