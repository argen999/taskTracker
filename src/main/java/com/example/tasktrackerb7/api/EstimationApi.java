package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.EstimationService;
import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/estimations")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Estimations api", description = "Estimations controller")
public class EstimationApi {

    private final EstimationService estimationService;

    @Operation(summary = "Create estimation", description = "Create estimation with card id")
    @PostMapping("/{id}")
    EstimationResponse createEstimation(@PathVariable Long id, @RequestBody EstimationRequest estimationRequest) {
        return estimationService.createEstimation(id, estimationRequest);
    }

    @Operation(summary = "Update estimation", description = "Update estimation with id")
    @PutMapping("/{id}")
    EstimationResponse updateEstimation(@PathVariable Long id, @RequestBody EstimationRequest estimationRequest) {
        return estimationService.updateEstimation(id, estimationRequest);
    }

    @Operation(summary = "Delete estimation", description = "Delete estimation with id")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteEstimation(@PathVariable Long id) {
        return estimationService.deleteEstimation(id);
    }

}
