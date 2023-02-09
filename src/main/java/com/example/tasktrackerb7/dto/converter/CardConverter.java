package com.example.tasktrackerb7.dto.converter;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.serviceimpl.ChecklistServiceImpl;
import com.example.tasktrackerb7.dto.request.*;
import com.example.tasktrackerb7.dto.response.*;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardConverter {

    private final CardRepository cardRepository;

    private final EstimationRepository estimationRepository;

    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    private final ChecklistRepository checklistRepository;

    private final ChecklistServiceImpl checklistService;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public CardInnerPageResponse convertToCardInnerPageResponse(Card card) {
        CardInnerPageResponse response = new CardInnerPageResponse();
        response.setId(card.getId());
        response.setName(card.getTitle());
        response.setDescription(card.getDescription());
        if (card.getLabels() != null) {
            response.setLabelResponses(labelRepository.getAllLabelResponse(card.getId()));
        }

        if (card.getEstimation() != null) {
            response.setEstimationResponse(getEstimationByCardId(card.getId()));
        }

        if (card.getUsers() != null) {
            response.setMemberResponses(getAllCardMembers(card.getUsers()));
        }

        response.setChecklistResponses(getChecklistResponses(checklistRepository.getAllChecklists(card.getId())));
        if (card.getComments() != null) {
            response.setCommentResponses(getCommentResponse(card.getComments()));
        }
        return response;
    }

    private List<CommentResponse> getCommentResponse(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                commentResponses.add(convertCommentToResponse(comment));
            }
        }
        return commentResponses;
    }

    private CommentResponse convertCommentToResponse(Comment comment) {
        User user = getAuthenticateUser();
        return new CommentResponse(comment.getId(), comment.getText(), LocalDateTime.now(), user.getName(), user.getSurname(), user.getPhotoLink());
    }

    private List<ChecklistResponse> getChecklistResponses(List<Checklist> checklists) {
        List<ChecklistResponse> checklistResponses = new ArrayList<>();
        if (checklists == null) {
            return checklistResponses;
        } else {
            for (Checklist checklist : checklists) {
                checklistResponses.add(checklistService.convertToResponse(checklist));
            }
        }
        return checklistResponses;
    }

    private EstimationResponse getEstimationByCardId(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card with id: " + " not found"));
        Estimation estimation = estimationRepository.findById(card.getEstimation().getId()).orElseThrow(() -> new NotFoundException("estimation with id: " + card.getEstimation().getId() + " not found"));
        return new EstimationResponse(estimation.getId(), estimation.getDateOfStart(), estimation.getDateOfFinish());
    }

    public List<CardResponse> convertToResponseForGetAll(List<Card> cards) {
        List<CardResponse> cardResponses = new ArrayList<>();
        if (cards == null) {
            return cardResponses;
        } else {
            for (Card card : cards) {
                CardResponse cardResponse = new CardResponse();
                cardResponse.setId(card.getId());
                cardResponse.setName(card.getTitle());
                cardResponse.setLabelResponses(labelRepository.getAllLabelResponse(card.getId()));

                if (card.getEstimation() != null) {
                    int between = Period.between(LocalDate.from(card.getEstimation().getDateOfStart()), LocalDate.from(card.getEstimation().getDateOfFinish())).getDays();
                    cardResponse.setDuration(" " + between + " days");
                }

                cardResponse.setNumOfMembers(card.getUsers().size());
                int item = 0;
                for (Checklist checklist : checklistRepository.getAllChecklists(card.getId())) {
                    for (int i = 0; i < checklist.getItems().size(); i++) {
                        item++;
                    }
                }

                cardResponse.setNumOfItems(item);
                int completedItems = 0;
                for (Checklist c : card.getChecklists()) {
                    for (Item item1 : c.getItems()) {
                        if (item1.getIsDone()) {
                            completedItems++;
                        }
                    }
                }

                cardResponse.setNumOfCompletedItems(completedItems);
                cardResponses.add(cardResponse);
            }
        }
        return cardResponses;
    }

    private List<MemberResponse> getAllCardMembers(List<User> users) {
        List<MemberResponse> memberResponses = new ArrayList<>();
        for (User user : users) {
            memberResponses.add(convertToMemberResponse(user));
        }
        return memberResponses;
    }

    private MemberResponse convertToMemberResponse(User user) {
        return new MemberResponse(user.getId(), user.getUsername(), user.getSurname(), user.getEmail(), user.getPhotoLink());
    }

    private User convertMemberToUser(MemberRequest memberRequest) {
        return userRepository.findByEmail(memberRequest.getEmail()).orElseThrow(() -> new NotFoundException("the user with this email was not found"));
    }

}
