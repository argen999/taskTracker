package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.AllIssuesService;
import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/allIssues")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "All issues api", description = "All issues api")
public class AllIssuesApi {

    private final AllIssuesService allIssuesService;

    @Operation(summary = "Get all issues", description = "Get all issues by workspace id")
    @GetMapping("/{id}")
    public AllIssuesResponseForGetAll getAll(@PathVariable Long id) {
        return allIssuesService.getAll(id);
    }

    @Operation(summary = "Filter by date", description = "Filter by date of start or date of finish")
    @GetMapping("/date/{id}")
    public AllIssuesResponseForGetAll filterByDateOfStart(@PathVariable Long id,
                                                          @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                          @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return allIssuesService.filterByDateOfStart(id, from, to);
    }

    @Operation(summary = "Filter by members", description = "Filter cards by members")
    @GetMapping("/{id}/{memberId}")
    public AllIssuesResponseForGetAll filterByMembers(@PathVariable Long id, @PathVariable Long memberId) {
        return allIssuesService.filterByMembers(id, memberId);
    }

    @Operation(summary = "Filter by label", description = "Filter cards by label")
    @GetMapping("/label/{id}/{labelId}")
    public AllIssuesResponseForGetAll filterByLabel(@PathVariable Long id, @PathVariable Long  labelId) {
        return allIssuesService.filterByLabel(id, labelId);
    }
}
