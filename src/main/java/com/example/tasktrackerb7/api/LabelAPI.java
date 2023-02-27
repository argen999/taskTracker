package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.LabelService;
import com.example.tasktrackerb7.dto.request.LabelRequest;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/labels")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Label API", description = "Label API")
public class LabelAPI {

    private final LabelService labelService;

    @Operation(summary = "Create new label", description = "Create new label")
    @PostMapping("/createLabel")
    public LabelResponse createLabel(@RequestBody LabelRequest labelRequest) {
        return labelService.createLabel(labelRequest);
    }

    @Operation(summary = "Get all labels by card ID", description = "Get all labels by cardID")
    @GetMapping("/getAllLabelsByCardId/{cardId}")
    public List<LabelResponse> getAllLabelsByCardId(@PathVariable Long cardId) {
        return labelService.getAllLabelsByCardId(cardId);
    }

    @Operation(summary = "Update label", description = "Update label")
    @PutMapping("/updateLabel/{id}")
    public LabelResponse updateLabel(@RequestBody LabelRequest labelRequest, @PathVariable Long id) {
        return labelService.updateLabel(labelRequest, id);
    }

    @Operation(summary = "Delete label by ID", description = "Delete label by ID")
    @DeleteMapping("/deleteLabelById/{id}")
    public SimpleResponse deleteLabelById(@PathVariable Long id) {
        return labelService.deleteLabelById(id);
    }

    @Operation(summary = "Delete label by ID from card", description = "Delete label by ID from card")
    @DeleteMapping("/deleteLabelByIdFromCard/{cardId}/{labelId}")
    public SimpleResponse deleteLabelByIdFromCard(@PathVariable Long cardId, @PathVariable Long labelId) {
        return labelService.deleteLabelByIdFromCard(cardId, labelId);
    }

    @Operation(summary = "Assign label to card", description = "Assign label to card")
    @PostMapping("/assignLabelToCard/{labelId}/{cardId}")
    public void assignLabelToCard(@PathVariable Long labelId, @PathVariable Long cardId) {
        labelService.assignLabelToCard(labelId, cardId);
    }
}
