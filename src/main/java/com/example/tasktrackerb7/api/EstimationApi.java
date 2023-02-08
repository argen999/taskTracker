package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.EstimationService;
import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/estimations")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Estimations api", description = "Estimations controller")
public class EstimationApi {

    private final EstimationService estimationService;

    @PostMapping("/{id}")
    EstimationResponse createEstimation(@PathVariable Long id, @RequestBody EstimationRequest estimationRequest) {
        return estimationService.createEstimation(id, estimationRequest);
    }

}
