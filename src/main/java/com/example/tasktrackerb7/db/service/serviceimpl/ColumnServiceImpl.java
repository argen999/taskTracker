package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Board;
import com.example.tasktrackerb7.db.entities.Column;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.BoardRepository;
import com.example.tasktrackerb7.db.repository.ColumnRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.service.ColumnService;
import com.example.tasktrackerb7.dto.request.ColumnRequest;
import com.example.tasktrackerb7.dto.response.ColumnResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColumnServiceImpl implements ColumnService {

    private final BoardRepository boardRepository;

    private final ColumnRepository columnRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> {
            throw new NotFoundException("User not found!");
        });
    }

    @Override
    public ColumnResponse create(Long boardId, ColumnRequest columnRequest) {
        User user = getAuthenticateUser();
        Column column = new Column(columnRequest);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            log.error("Board not found!");
            throw new NotFoundException("Board not found!");
        });

        if (board.getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), board.getWorkspace().getId()))) {

            board.getColumns().add(column);
            column.setBoard(board);
            columnRepository.save(column);
            log.info("Column successfully created");
        } else throw new BadRequestException("You are not member in this workspace!");

        return new ColumnResponse(column.getId(), column.getName());
    }

    @Override
    public ColumnResponse update(Long id, ColumnRequest columnRequest) {
        User user = getAuthenticateUser();

        Column column = columnRepository.findById(id).orElseThrow(() -> {
            log.error("Column not found!");
            throw new NotFoundException("Column not found!");
        });

        if (column.getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), column.getBoard().getWorkspace().getId()))) {

            column.setName(columnRequest.getName());
            columnRepository.save(column);
            log.info("Column successfully updated");
        } else throw new BadRequestException("You are not member in this workspace!");

        return new ColumnResponse(column.getId(), column.getName());
    }

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();

        Column column = columnRepository.findById(id).orElseThrow(() -> {
            log.error("Column not found!");
            throw new NotFoundException("Column not found!");
        });

        if (column.getBoard().getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), column.getBoard().getWorkspace().getId()))) {

            columnRepository.delete(column);
            log.info("Column  successfully deleted");
        } else throw new BadRequestException("You are not member in this workspace!");

        return new SimpleResponse("This column deleted successfully");
    }
}