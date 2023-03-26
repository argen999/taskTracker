package com.example.tasktrackerb7.db.service.serviceimpl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;
    private final LabelResponseConverter labelResponseConverter;

    @Override
    public SimpleResponse createLabel(LabelRequest labelRequest) {
        Label label = new Label();
        label.setDescription(labelRequest.getDescription());
        label.setColor(labelRequest.getColor());
        labelRepository.save(label);
        log.info("Label successfully created!");
        return new SimpleResponse("Label successfully created!");
    }

    @Override
    public LabelResponse getLabelById(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(
                () ->{
                    log.error("Label with ID " + id + " not found!");
                    throw new NotFoundException("Label with ID " + id + " not found!");
                }
        );
        return labelResponseConverter.viewLabel(label);
    }

    @Override
    public SimpleResponse updateLabel(Long id, LabelRequest labelRequest) {
        Label label = labelRepository.findById(id).orElseThrow(
                () ->{
                    log.error("Label with ID " + id + " not found!");
                    throw new NotFoundException("Label with ID " + id + " not found!");
                }
        );
        label.setDescription(labelRequest.getDescription());
        label.setColor(labelRequest.getColor());
        labelRepository.save(label);
        log.info("Label updated successfully!");
        return new SimpleResponse("Label updated successfully!");
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

    @Override
    public List<LabelResponse> getAllLabel() {
        List<LabelResponse> labels =  labelRepository.getAllLabelResponse();
        return labels.stream().map(l -> new LabelResponse(l.getId(),l.getDescription(),l.getColor())).toList();
    }


}
