package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.CardService;
import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardRequest;
import com.example.tasktrackerb7.dto.response.BoardInnerPageResponse;
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

    private final CardService cardService;

    @Operation(summary = "Create", description = "Create card")
    @PostMapping
    public CardInnerPageResponse create(@RequestBody CardRequest cardRequest) {
        return cardService.create(cardRequest);
    }

    @Operation(summary = "Update", description = "Update card(if isName = true - update title, id isNam = false = update description")
    @PatchMapping
    public CardInnerPageResponse update(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
        return cardService.update(updateCardRequest);
    }

    @Operation(summary = "Get by id", description = "Get card by id")
    @GetMapping("/{id}")
    public CardInnerPageResponse getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @Operation(summary = "Get all cards", description = "Get all cards by board id")
    @GetMapping("/all/{id}")
    public BoardInnerPageResponse getAll(@PathVariable Long id) {
        return cardService.getAll(id);
    }

    @Operation(summary = "Delete", description = "Delete card by id")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return cardService.delete(id);
    }

    @Operation(summary = "Archive", description = "Archive card by id")
    @PatchMapping("/{id}")
    public SimpleResponse archive(@PathVariable Long id) {
        return cardService.archive(id);
    }

    @Operation(summary = "Get all archived cards", description = "Get all archived cards by board id")
    @GetMapping("/archive/{id}")
    public List<CardResponse> getAllArchiveCards(@PathVariable Long id) {
        return cardService.getAllArchivedCards(id);
    }
}
