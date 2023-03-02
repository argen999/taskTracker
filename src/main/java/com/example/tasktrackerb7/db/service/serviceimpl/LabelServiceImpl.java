package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.converter.requestConverter.LabelRequestConverter;
import com.example.tasktrackerb7.converter.responseConverter.LabelResponseConverter;
import com.example.tasktrackerb7.db.entities.Card;
import com.example.tasktrackerb7.db.entities.Label;
import com.example.tasktrackerb7.db.repository.CardRepository;
import com.example.tasktrackerb7.db.repository.LabelRepository;
import com.example.tasktrackerb7.db.service.LabelService;
import com.example.tasktrackerb7.dto.request.LabelRequest;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;
    private final LabelRequestConverter labelRequestConverter;
    private final LabelResponseConverter labelResponseConverter;

    @Override
    public LabelResponse createLabel(LabelRequest labelRequest) {
        Label label = labelRequestConverter.createLabel(labelRequest);
        labelRepository.save(label);
        return labelResponseConverter.viewLabel(label);
    }

    @Override
    public List<LabelResponse> getAllLabelsByCardId(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card with ID " + cardId + " not found!")
        );
        List<LabelResponse> labelResponses = card.getLabels().stream().map(l -> new LabelResponse(l.getId(), l.getDescription(), l.getColor())).toList();
        return labelResponses;

    }

    @Override
    public LabelResponse updateLabel(Long id, LabelRequest labelRequest) {
        Label label = labelRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Label with ID " + id + " not found!")
        );
        labelRequestConverter.updateLabel(label, labelRequest);
        labelRepository.save(label);
        return labelResponseConverter.viewLabel(label);
    }

    @Override
    public SimpleResponse deleteLabelById(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Label wit ID " + id + " not found!")
        );
        labelRepository.deleteLabelById(id);
        return new SimpleResponse("Label with ID " + id + " deleted successfully!");
    }

    @Override
    public SimpleResponse deleteLabelByIdFromCard(Long cardId, Long labelId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card with ID " + cardId + " not found!")
        );
        Label label = labelRepository.findById(labelId).orElseThrow(
                () -> new NotFoundException("Label with ID " + labelId + " not found!")
        );
        if (card.getLabels().contains(label)) {
            card.deleteLabel(label);
        } else {
            throw new NotFoundException("Label with ID " + labelId + " is not present in card with ID " + cardId);
        }
        return new SimpleResponse("Label with ID " + labelId + " deleted successfully from card with ID " + cardId);
    }

    @Override
    public void assignLabelToCard(Long labelId, Long cardId) {
        Label label = labelRepository.findById(labelId).orElseThrow(
                () -> new NotFoundException("Label with ID " + labelId + " not found!")
        );
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card with ID " + cardId + " not found!")
        );
        if (card.getLabels().contains(label)) {
            throw new BadRequestException("Label already exists on this card!");
        }
        card.assignLabel(label);
        labelRepository.save(label);
    }
}
