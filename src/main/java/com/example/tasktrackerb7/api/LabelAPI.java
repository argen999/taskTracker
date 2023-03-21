package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.LabelService;
import com.example.tasktrackerb7.dto.request.LabelRequest;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/labels")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Label API", description = "Label API")
public class LabelAPI {

    private final LabelService labelService;

    @Operation(summary = "Create new label", description = "Create new label")
    @PostMapping()
    public SimpleResponse createLabel(@RequestBody LabelRequest labelRequest) {
        return labelService.createLabel(labelRequest);
    }

    @Operation(summary = "Get label by ID", description = "Get label by ID")
    @GetMapping("/{id}")
    public LabelResponse getLabelById(@PathVariable Long id) {
        return labelService.getLabelById(id);
    }

    @Operation(summary = "Update label", description = "Update label")
    @PutMapping("/{id}")
    public SimpleResponse updateLabel(@RequestBody LabelRequest labelRequest, @PathVariable Long id) {
        return labelService.updateLabel(id, labelRequest);
    }

    @Operation(summary = "Delete label by ID", description = "Delete label by ID")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteLabelById(@PathVariable Long id) {
        return labelService.deleteLabelById(id);
    }

    @Operation(summary = "Delete label by ID from card", description = "Delete label by ID from card")
    @DeleteMapping("/delete/{cardId}/{labelId}")
    public SimpleResponse deleteLabelByIdFromCard(@PathVariable Long cardId, @PathVariable Long labelId) {
        return labelService.deleteLabelByIdFromCard(cardId, labelId);
    }

    @Operation(summary = "Assign label to card", description = "Assign label to card")
    @PostMapping("/{labelId}/{cardId}")
    public void assignLabelToCard(@PathVariable Long labelId, @PathVariable Long cardId) {
        labelService.assignLabelToCard(labelId, cardId);
    }

    @Operation(summary = "Get all", description = "Get all label")
    @GetMapping()
    public List<LabelResponse> getAllLabel() {
        return labelService.getAllLabel();
    }
}
