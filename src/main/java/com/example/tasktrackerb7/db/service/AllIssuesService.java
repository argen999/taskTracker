package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface AllIssuesService {

    AllIssuesResponseForGetAll getAll(Long id, LocalDate from, LocalDate to, Long memberId, Long labelId, Boolean isFilter);

}
