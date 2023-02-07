package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.ChecklistService;
import com.example.tasktrackerb7.dto.request.ChecklistRequest;
import com.example.tasktrackerb7.dto.request.UpdateChecklistRequest;
import com.example.tasktrackerb7.dto.response.ChecklistResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/checklists")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Checklist api", description = "Checklist Api")
public class ChecklistApi {

    private final ChecklistService checklistService;

    @Operation(summary = "Create", description = "Create checklist")
    @PostMapping("/{id}")
    public ChecklistResponse create(@PathVariable Long id, @RequestBody ChecklistRequest checklistRequest) {
        return checklistService.create(id, checklistRequest);
    }

    @Operation(summary = "Update", description = "Update checklist title")
    @PatchMapping("/{id}")
    public ChecklistResponse update(@RequestBody @Valid UpdateChecklistRequest updateChecklistRequest) {
        return checklistService.update(updateChecklistRequest);
    }

    @Operation(summary = "Find all", description = "Get all checklists by card is")
    @GetMapping("/{id}")
    public List<ChecklistResponse> getAll(@PathVariable Long id) {
        return checklistService.findAllChecklistsByCardId(id);
    }

    @Operation(summary = "Delete", description = "Delete checklist")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return checklistService.delete(id);
    }


}
