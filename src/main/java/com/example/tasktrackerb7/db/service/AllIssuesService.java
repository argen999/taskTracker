package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface AllIssuesService {

    AllIssuesResponseForGetAll getAll(Long id);

    AllIssuesResponseForGetAll filterByDateOfStart(Long id, LocalDate from, LocalDate to);

    AllIssuesResponseForGetAll filterByMembers(Long id, Long memberId);

    AllIssuesResponseForGetAll filterByLabel(Long id, Long  labelId);
}
