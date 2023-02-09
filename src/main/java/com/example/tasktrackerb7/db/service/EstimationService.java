package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.dto.request.EstimationRequest;
import com.example.tasktrackerb7.dto.response.EstimationResponse;

public interface EstimationService {

    EstimationResponse createEstimation(Long id, EstimationRequest estimationRequest);

}
