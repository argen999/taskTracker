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
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardConverter {

    private final ColumnRepository columnRepository;

    private final CardRepository cardRepository;

    private final EstimationRepository estimationRepository;

    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ChecklistRepository checklistRepository;

    private final NotificationRepository notificationRepository;

    private final WorkspaceRepository workspaceRepository;

    private final CommentRepository commentRepository;

    private final ChecklistServiceImpl checklistService;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Card convertRequestToEntity(CardRequest cardRequest) {
        User user = getAuthenticateUser();
        Column column = columnRepository.findById(cardRequest.getColumnId()).orElseThrow(() ->
                new NotFoundException("column with id: " + cardRequest.getColumnId() + " not found"));
        Board board = boardRepository.findById(column.getBoard().getId()).orElseThrow(() ->
                new NotFoundException("board with id: " + column.getBoard().getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(board.getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("workspace with id: " + board.getWorkspace().getId() + " not found"));
        Card card = new Card();

        card.setTitle(cardRequest.getName());
        card.setArchive(false);
        card.setCreated(LocalDate.now());
        card.setDescription(cardRequest.getDescription());
        card.setColumn(column);

        if (cardRequest.getEstimationRequest() != null) {
            Estimation estimation = new Estimation();
            estimation.setDateOfStart(cardRequest.getEstimationRequest().getDateOfStart());
            estimation.setDateOfFinish(cardRequest.getEstimationRequest().getDateOfFinish());
            estimation.setCard(card);
            card.setEstimation(estimation);
            estimationRepository.save(estimation);
        }

        List<MemberResponse> members = new ArrayList<>();
        for (UserWorkspaceRole u : workspace.getMembers()) {
            if (!user.equals(u.getUser())) {
                members.add(convertToMemberResponse(u.getUser()));
            }
        }

        for (ChecklistRequest checklistRequest : cardRequest.getChecklistRequests()) {
            Checklist checklist = new Checklist(checklistRequest.getName());

            for (ItemRequest itemRequest : checklistRequest.getItemRequests()) {
                Item item = new Item(itemRequest.getText(), itemRequest.isDone());
                item.setChecklist(checklist);
                checklist.addItem(item);
            }
            card.addChecklist(checklist);
            checklist.setCard(card);
            checklistRepository.save(checklist);
        }

        for (LabelRequest labelRequest : cardRequest.getLabelRequests()) {
            Label label = labelRepository.findById(labelRequest.getId()).orElseThrow(() ->
                    new NotFoundException("label with id: " + labelRequest.getId() + " not found"));
            label.setCard(card);
            card.addLabel(label);
        }

        for (CommentRequest commentRequest : cardRequest.getCommentRequests()) {
            Comment comment = new Comment(commentRequest.getText(), LocalDateTime.now());
            comment.setUser(user);
            comment.setCard(card);
            card.addComment(comment);
            commentRepository.save(comment);
        }

        for (MemberResponse memberResponse : members) {
            for (MemberRequest memberRequest : cardRequest.getMemberRequests()) {
                if (memberResponse.getEmail().equals(memberRequest.getEmail())) {
                    card.addMember(convertMemberToUser(memberRequest));
                    Notification notification = new Notification();
                    notification.setCard(card);
                    notification.setStatus(false);
                    notification.setUser(convertMemberToUser(memberRequest));
                    notification.setFromUser(user);
                    notification.setDateOfWrite(LocalDateTime.now());
                    notification.setText("You has assigned in " + card.getTitle() + " card, by " + user.getName() + " " + user.getSurname());
                    notificationRepository.save(notification);
                    User recipient = convertMemberToUser(memberRequest);
                    recipient.addNotification(notification);
                }
            }
        }

        return card;
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
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id: " + " not found"));
        Estimation estimation = estimationRepository.findById(card.getEstimation().getId()).orElseThrow(() ->
                new NotFoundException("estimation with id: " + card.getEstimation().getId() + " not found"));
        return new EstimationResponse(estimation.getId(), estimation.getDateOfStart(), estimation.getDateOfFinish());
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
