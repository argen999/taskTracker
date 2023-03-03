package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.LabelRequest;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface LabelService {

    SimpleResponse createLabel(LabelRequest labelRequest);

    LabelResponse getLabelById(Long id);

    SimpleResponse updateLabel(Long id,LabelRequest labelRequest);

    SimpleResponse deleteLabelById(Long id);

    SimpleResponse deleteLabelByIdFromCard(Long cardId, Long labelId);

    void assignLabelToCard(Long labelId, Long cardId);


}
