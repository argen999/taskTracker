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

    @Operation(summary = "Get all issues", description = "Get  all issues by workspace id")
    @GetMapping("/{id}")
    public AllIssuesResponseForGetAll getAll(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                             @RequestParam(required = false) Long memberId, @RequestParam(required = false) Long labelId, Boolean isFilter) {
        return allIssuesService.getAll(id, from, to, memberId, labelId, isFilter);
    }
}
