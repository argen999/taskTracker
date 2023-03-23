package com.example.tasktrackerb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AllIssuesResponseForGetAll {

    private Set<AllIssuesResponse> allIssuesResponses;
}
