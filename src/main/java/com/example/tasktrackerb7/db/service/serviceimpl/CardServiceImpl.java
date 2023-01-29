package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.CardService;
import com.example.tasktrackerb7.dto.converter.CardConverter;
import com.example.tasktrackerb7.dto.request.*;
import com.example.tasktrackerb7.dto.response.*;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
            Card card = converter.convertRequestToEntity(cardRequest);
            card.setColumn(card.getColumn());
            return converter.convertToCardInnerPageResponse(cardRepository.save(card));
        } else {
            throw new BadRequestException("you are not member in workspace with id: " + workspace.getId());
        }
    }

    public CardInnerPageResponse updateTitle(UpdateCardTitleRequest request) {
        Card card = cardRepository.findById(request.getId()).orElseThrow(() ->
                new NotFoundException("Card with id: " + request.getId()  + " not found"));
        card.setTitle(request.getTitle());
        return converter.convertToCardInnerPageResponse(card);
    }



}
