package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface EstimationService {

    EstimationResponse createEstimation(Long id, EstimationRequest estimationRequest);

    EstimationResponse updateEstimation(Long id, EstimationRequest estimationRequest);

    SimpleResponse deleteEstimation(Long id);

}
