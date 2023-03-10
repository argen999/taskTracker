package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.AllIssuesService;
import com.example.tasktrackerb7.dto.request.AllIssuesRequest;
import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/allIssues")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "All issues api", description = "All issues api")
public class AllIssuesApi {

    private final AllIssuesService allIssuesService;

    @Operation(summary = "Get all issues", description = "Get all issues by workspace id")
    @GetMapping("/{id}")
    public AllIssuesResponseForGetAll getAll(@PathVariable Long id, @RequestBody AllIssuesRequest allIssuesRequest) {
        return allIssuesService.getAll(id, allIssuesRequest);
    }
}
