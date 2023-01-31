package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.serviceimpl.CardServiceImpl;
import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardTitleRequest;
import com.example.tasktrackerb7.dto.response.CardInnerPageResponse;
import com.example.tasktrackerb7.dto.response.CardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cards")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Card api", description = "Card Api")
public class CardApi {

    private final CardServiceImpl cardService;

    @Operation(summary = "Create", description = "Create card")
    @PostMapping
    public CardInnerPageResponse create(@RequestBody CardRequest cardRequest) {
        return cardService.create(cardRequest);
    }

    @Operation(summary = "Update", description = "Update card title")
    @PatchMapping
    public CardInnerPageResponse updateTitle(@RequestBody @Valid UpdateCardTitleRequest updateCardTitleRequest) {
        return cardService.updateTitle(updateCardTitleRequest);
    }

    @Operation(summary = "Get by id", description = "Get card By id")
    @GetMapping("/{id}")
    public CardInnerPageResponse getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @Operation(summary = "Get all by column id", description = "Get all cards by column id")
    @GetMapping("/all/{id}")
    public List<CardResponse> getAllByColumnId(@PathVariable Long id) {
        return cardService.getAllCardsByColumnId(id);
    }

    @Operation(summary = "Delete", description = "Delete card by id")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return cardService.delete(id);
    }

}
