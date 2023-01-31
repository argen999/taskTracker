package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.ChecklistRequest;
import com.example.tasktrackerb7.dto.request.UpdateChecklistRequest;
import com.example.tasktrackerb7.dto.response.ChecklistResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChecklistService {

    ChecklistResponse create(Long id, ChecklistRequest checklistRequest);

    ChecklistResponse update(UpdateChecklistRequest updateChecklistRequest);

    SimpleResponse delete(Long id);

    List<ChecklistResponse> findAllChecklistsByCardId(Long id);
}
