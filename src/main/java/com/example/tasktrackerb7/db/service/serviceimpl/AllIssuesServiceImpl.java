package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.CardRepository;
import com.example.tasktrackerb7.db.repository.LabelRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.db.service.AllIssuesService;
import com.example.tasktrackerb7.dto.response.AllIssuesResponse;
import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import com.example.tasktrackerb7.dto.response.CardMemberResponse;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AllIssuesServiceImpl implements AllIssuesService {

    private final WorkspaceRepository workspaceRepository;

    private final CardRepository cardRepository;

    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    public AllIssuesResponseForGetAll getAll(Long id, LocalDate from, LocalDate to, Long memberId, Long labelId, Boolean isFilter) { //workspaceId
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new NotFoundException("Workspace with id: " + id + " not found"));
        AllIssuesResponseForGetAll allIssuesResponseForGetAll = new AllIssuesResponseForGetAll();
        Set<AllIssuesResponse> allIssuesResponses = new HashSet<>();
        User user = new User();
        if (memberId != null && isFilter.equals(true) && labelId == null) {
            user = userRepository.findById(memberId).get();
            List<Card> cards = workspace.getAllIssues();
            for (Card card : cards) {
                for (User member : card.getUsers()) {
                    if (member.equals(user)) {
                        allIssuesResponses.add(convertToResponse(card));
                    }
                }
            }
            allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses);
        }

        if (labelId != null && isFilter.equals(true) && memberId == null) {
            Label label = labelRepository.findById(labelId).orElseThrow(() -> new NotFoundException("Label with id: " + labelId + " not found"));
            List<Card> cards = workspace.getAllIssues();
            for (Card card : cards) {
                if (card.getLabels().contains(label)) {
                    allIssuesResponses.add(convertToResponse(card));
                }
            }
            allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses);
        }

        if (labelId != null && memberId != null && isFilter.equals(true)) {
            user = userRepository.findById(memberId).get();
            List<Card> cards = workspace.getAllIssues();
            Label label = labelRepository.findById(labelId).orElseThrow(() -> new NotFoundException("Label with id: " + labelId + " not found"));
            for (Card card : cards) {
                for (User member : card.getUsers()) {
                    if (member.equals(user)) {
                        if (card.getLabels().contains(label)) {
                            allIssuesResponses.add(convertToResponse(card));
                        }
                    }
                }
            }
            allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses);
        }

        if (isFilter.equals(false)) {
            for (Card card : workspace.getAllIssues()) {
                allIssuesResponses.add(convertToResponse(card));
            }
            allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses);
        }

        if (from != null || to != null && isFilter.equals(true)) {
            if (from.isAfter(to)) {
                throw new BadCredentialsException("date of start need to be after date of finish");
            }
            allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses(cardRepository.searchCardByCreatedAt(id, from, to)));
        }

        return allIssuesResponseForGetAll;
    }


    private Set<AllIssuesResponse> allIssuesResponses(Set<Card> cards) {
        Set<AllIssuesResponse> responses = new HashSet<>();
        for (Card card : cards) {
            responses.add(convertToResponse(card));
        }
        return responses;
    }

    private AllIssuesResponse convertToResponse(Card card) {
        AllIssuesResponse response = new AllIssuesResponse(card);
        Set<CardMemberResponse> cardMemberResponses = new HashSet<>();
        int isDoneItems = 0;
        int allItems = 0;

        if (card.getEstimation() != null) {
            int between = Period.between(LocalDate.from(card.getEstimation().getDateOfStart()), LocalDate.from(card.getEstimation().getDateOfFinish())).getDays();
            response.setPeriod(between);
        }

        for (User user : card.getUsers()) {
            cardMemberResponses.add(new CardMemberResponse(user));
        }
        response.setCardMemberResponses(cardMemberResponses);

        List<LabelResponse> labelResponses = new ArrayList<>();
        for (Label l : card.getLabels()) {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setId(l.getId());
            labelResponse.setDescription(l.getDescription());
            labelResponse.setColor(l.getColor());
            labelResponses.add(labelResponse);
        }
        response.setLabelResponses(labelResponses);

        for (Checklist checklist : card.getChecklists()) {
            for (Item item : checklist.getItems()) {
                allItems++;
                if (item.getIsDone().equals(true)) {
                    isDoneItems++;
                }
            }
            String checklist1 = isDoneItems + "/" + allItems;
            response.setChecklist(checklist1);
        }
        return response;
    }
}