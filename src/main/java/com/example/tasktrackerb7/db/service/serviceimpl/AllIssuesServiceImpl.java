package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.AllIssuesService;
import com.example.tasktrackerb7.dto.response.AllIssuesResponse;
import com.example.tasktrackerb7.dto.response.AllIssuesResponseForGetAll;
import com.example.tasktrackerb7.dto.response.CardMemberResponse;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AllIssuesServiceImpl implements AllIssuesService {

    private final WorkspaceRepository workspaceRepository;

    private final CardRepository cardRepository;

    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public AllIssuesResponseForGetAll getAll(Long id) { //workspaceId
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + id + " not found"));
        AllIssuesResponseForGetAll allIssuesResponseForGetAll = new AllIssuesResponseForGetAll();
        List<AllIssuesResponse> allIssuesResponses = new ArrayList<>();

        for (Card card : workspace.getAllIssues()) {
            allIssuesResponses.add(convertToResponse(card));
        }

        Boolean isAdmin = false;
        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            isAdmin = true;
        }

        allIssuesResponseForGetAll.setIsAdmin(isAdmin);
        allIssuesResponseForGetAll.setAllIssuesResponses(allIssuesResponses);

        return allIssuesResponseForGetAll;
    }

    public AllIssuesResponseForGetAll filterByDateOfStart(Long id, LocalDate from, LocalDate to) {
        AllIssuesResponseForGetAll response = new AllIssuesResponseForGetAll();
        if (from != null && to != null) {
            if (from.isAfter(to)) {
                throw new BadCredentialsException("date of start need to be after date of finish");
            }
            response.setAllIssuesResponses(allIssuesResponses(cardRepository.searchCardByCreatedAt(id, from, to)));
        }
        return response;
    }

    private List<AllIssuesResponse> allIssuesResponses(List<Card> cards) {
        List<AllIssuesResponse> responses = new ArrayList<>();
        for (Card card : cards) {
            responses.add(convertToResponse(card));
        }
        return responses;
    }

    private AllIssuesResponse convertToResponse(Card card) {
        AllIssuesResponse response = new AllIssuesResponse(card);
        List<CardMemberResponse> cardMemberResponses = new ArrayList<>();
        int isDoneItems = 0;
        int allItems = 0;

        int period = Period.between(card.getEstimation().getDateOfStart(), card.getEstimation().getDateOfFinish()).getDays();
        response.setPeriod(period);

        for (User user : card.getUsers()) {
            cardMemberResponses.add(new CardMemberResponse(user));
        }

        response.setCardMemberResponses(cardMemberResponses);

        List<LabelResponse> labelResponses = labelRepository.getAllLabelResponse(card.getId());
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
