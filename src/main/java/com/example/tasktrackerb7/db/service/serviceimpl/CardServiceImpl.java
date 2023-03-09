package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.entities.enums.NotificationType;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.CardService;
import com.example.tasktrackerb7.dto.converter.CardConverter;
import com.example.tasktrackerb7.dto.request.CardRequest;
import com.example.tasktrackerb7.dto.request.UpdateCardRequest;
import com.example.tasktrackerb7.dto.response.*;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final ColumnRepository columnRepository;

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final WorkspaceRepository workspaceRepository;

    private final CardConverter converter;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final NotificationRepository notificationRepository;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public CardInnerPageResponse create(CardRequest cardRequest) {
        User user = getAuthenticateUser();
        Column column = columnRepository.findById(cardRequest.getColumnId()).orElseThrow(() ->
                new NotFoundException("Column with id: " + cardRequest.getColumnId() + " not found"));
        Board board = boardRepository.findById(column.getBoard().getId()).orElseThrow(() ->
                new NotFoundException("Board with id: " + column.getBoard().getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(board.getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + board.getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            Card card = new Card(cardRequest.getName());
            card.setColumn(column);
            column.addCard(card);
            card.setCreated(LocalDate.now());
            card.setArchive(false);
            card.addUser(user);
            user.addCard(card);
            return converter.convertToCardInnerPageResponse(cardRepository.save(card));
        } else {
            throw new BadRequestException("you are not member in workspace with id: " + workspace.getId());
        }
    }

    @Override
    public CardInnerPageResponse update(UpdateCardRequest request) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(request.getId()).orElseThrow(() ->
                new NotFoundException("Card with id: " + request.getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(card.getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("workspace with id: " + card.getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            if (!request.getIsName()) {
                card.setTitle(request.getValue());
            } else {
                card.setDescription(request.getValue());
            }

            if (!userRepository.getAll(card.getId()).isEmpty()) {
                for (User u : userRepository.getAll(card.getId())) {
                    Notification notification = new Notification();
                    notification.setCard(card);
                    notification.setColumn(card.getColumn());
                    notification.setBoard(card.getColumn().getBoard());
                    notification.setDateOfWrite(LocalDateTime.now());
                    notification.setStatus(false);
                    notification.setFromUser(user);
                    notification.setText("Card updated!");
                    notification.setNotificationType(NotificationType.CARD);
                    u.addCard(card);
                    card.addUser(u);
                    if (!u.equals(user)) {
                        u.addNotification(notification);
                        notification.setUser(u);
                        notificationRepository.save(notification);
                    }
                }
            }
            return converter.convertToCardInnerPageResponse(card);
        } else {
            throw new BadRequestException("you are not member in this workspace");
        }
    }

    @Override
    public CardInnerPageResponse getById(Long id) {
        return converter.convertToCardInnerPageResponse(cardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id " + id + " not found")));
    }

    @Override
    public BoardInnerPageResponse getAll(Long id) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Board with id: " + id + " not found"));
        Workspace workspace = workspaceRepository.findById(board.getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + board.getWorkspace().getId() + " not found"));
        BoardInnerPageResponse boardInnerPageResponse = new BoardInnerPageResponse();
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            List<ColumnResponse> columnResponses = new ArrayList<>();
            for (Column column : board.getColumns()) {
                for (Card card : column.getCards()) {
                    if (card.getArchive().equals(false)) {
                        ColumnResponse columnResponse = new ColumnResponse();
                        columnResponse.setId(column.getId());
                        columnResponse.setName(column.getName());
                        columnResponse.setCardResponses(converter.convertToResponseForGetAll(column.getCards()));
                        columnResponses.add(columnResponse);
                    }
                }
            }
            boardInnerPageResponse.setBoardName(board.getName());
            boardInnerPageResponse.setColumnResponses(columnResponses);
        } else {
            throw new BadCredentialsException("You are not member in this workspace");
        }
        return boardInnerPageResponse;
    }

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id: " + id + " not found"));
        Workspace workspace = workspaceRepository.findById(card.getColumn().getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + card.getColumn().getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            if (user.getCards() != null) {
                for (Notification notification1 : user.getNotifications()) {
                    if (Objects.equals(notification1.getCard().getId(), id)) {
                        cardRepository.delete(card);
                    }
                }

            }

        } else {
            throw new BadCredentialsException("You are not member in this workspace");
        }
        if (!userRepository.getAll(card.getId()).isEmpty()) {
            for (User u : userRepository.getAll(card.getId())) {
                Notification n = new Notification();
                n.setCard(card);
                n.setColumn(card.getColumn());
                n.setBoard(card.getColumn().getBoard());
                n.setDateOfWrite(LocalDateTime.now());
                n.setStatus(false);
                n.setFromUser(user);
                n.setText("Card deleted!");
                n.setNotificationType(NotificationType.CARD);
                u.addCard(card);
                card.addUser(u);
                if (!u.equals(user)) {
                    u.addNotification(n);
                    n.setUser(u);
                    notificationRepository.save(n);
                }
            }
        }
        return new SimpleResponse("Card with id: " + id + " deleted successfully");
    }

    @Override
    public SimpleResponse archive(Long id) {
        User user = getAuthenticateUser();
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id: " + id + " not found"));
        Column column = columnRepository.findById(card.getColumn().getId()).orElseThrow(() ->
                new NotFoundException("Column with id: " + card.getColumn().getId() + " not found"));
        Workspace workspace = workspaceRepository.findById(column.getBoard().getWorkspace().getId()).orElseThrow(() ->
                new NotFoundException("Workspace with id: " + column.getBoard().getWorkspace().getId() + " not found"));
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            card.setArchive(!card.getArchive());
            cardRepository.save(card);
        } else {
            throw new BadCredentialsException("You are not member in this workspace");
        }
        return new SimpleResponse("Card : " + card.getTitle() + " archive");
    }

    @Override
    public List<CardResponse> getAllArchivedCards(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Board with id: " + id + " not found"));
        List<CardResponse> cardResponses = new ArrayList<>();
        for (Column column : board.getColumns()) {
            for (Card card : column.getCards()) {
                if (card.getArchive().equals(true)) {
                    cardResponses.add(converter.convertToResponseForGetAllArchived(card));
                }
            }
        }
        return cardResponses;
    }
}