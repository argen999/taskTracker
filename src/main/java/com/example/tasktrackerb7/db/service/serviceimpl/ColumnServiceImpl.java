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
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;
    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        System.out.println(login);
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public ColumnResponse create(Long boardId, ColumnRequest columnRequest) {
        User user = getAuthenticateUser();
        Column column = new Column();

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("Board not found!")
        );

        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), board.getWorkspace().getId()).getRole().getName().equals("ADMIN")) {
            board.getColumns().add(column);
            column.setBoard(board);
            columnRepository.save(column);
        } else throw new BadCredentialsException("You cannot delete because you do not have permission to do so!");

        return new ColumnResponse(
                column.getId(),
                column.getName()
        );
    }

    @Override
    public ColumnResponse update(Long id, ColumnRequest columnRequest) {
        User user = getAuthenticateUser();

        Column column = columnRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Column not found!")
        );

        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), column.getBoard().getWorkspace().getId()).getRole().getName().equals("ADMIN")) {
            column.setName(columnRequest.getName());
            columnRepository.save(column);
        } else throw new BadRequestException("You cannot delete because you do not have permission to do so!");

        return new ColumnResponse(
                column.getId(),
                column.getName()
        );
    }

    @Override
    public ColumnResponse delete(Long id) {
        User user = getAuthenticateUser();

        Column column = columnRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Column not found!")
        );

        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), column.getBoard().getWorkspace().getId()).getRole().getName().equals("ADMIN")) {
            columnRepository.delete(column);
        } else throw new BadRequestException("You cannot delete because you do not have permission to do so!");

        return new ColumnResponse(
                column.getId(),
                column.getName()
        );
    }

    @Override
    public List<ColumnResponse> getAll(Long boardId) {
        List<Column> columns = boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("Columns not found!")
        ).getColumns();

        return columns.stream().map(x -> new ColumnResponse(x.getId(), x.getName())).toList();
    }
}
