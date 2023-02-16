package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import org.springframework.stereotype.Service;

@Service
public interface AllIssuesService {

    AllIssuesResponseForGetAll getAll(Long id);
}
