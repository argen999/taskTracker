package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface EstimationService {

    EstimationResponse createEstimation(EstimationRequest estimationRequest);

    EstimationResponse updateEstimation(EstimationRequest estimationRequest);

    SimpleResponse deleteEstimation(Long id);

}
