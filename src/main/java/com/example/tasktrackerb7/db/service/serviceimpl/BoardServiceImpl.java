package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Board;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.BoardRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.db.service.BoardService;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        System.out.println(login);
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public BoardResponse create(BoardRequest boardRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(boardRequest.getWorkspaceId()).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );

        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            Board board = new Board(boardRequest);
            workspace.addBoard(board);
            board.setWorkspace(workspace);
            boardRepository.save(board);
            return new BoardResponse(
                    board.getId(),
                    board.getName(),
                    board.getBackground(),
                    board.getFavourite()
            );
        } else {
            throw new BadRequestException("you can't create");
        }
    }


    @Override
    public BoardResponse updateName(Long id, BoardRequest boardRequest) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
        Workspace workspace = workspaceRepository.findById(boardRequest.getWorkspaceId()).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            board.setName(boardRequest.getName());
            boardRepository.save(board);
            return new BoardResponse(
                    board.getId(),
                    board.getName(),
                    board.getBackground(),
                    board.getFavourite()
            );
        } else {
            throw new BadRequestException("you can't update name");
        }
    }

    @Override
    public BoardResponse updateBackground(Long id, BoardRequest boardRequest) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
        Workspace workspace = workspaceRepository.findById(boardRequest.getWorkspaceId()).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            board.setBackground(boardRequest.getBackground());
            boardRepository.save(board);
            return new BoardResponse(
                    board.getId(),
                    board.getName(),
                    board.getBackground(),
                    board.getFavourite()
            );
        } else {
            throw new BadRequestException("you can't update background");
        }
    }

    @Override
    public SimpleResponse delete(Long id, Long workspaceId) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        if (userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()).getRole().getName().equals("ADMIN")) {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> {
                        throw new NotFoundException("board not found");
                    }
            );
            boardRepository.delete(board);
            return new SimpleResponse("board deleted successfully");
        } else {
            throw new BadRequestException("you can't delete board");
        }
    }

    @Override
    public List<BoardResponse> getAllByWorkspaceId(Long id) {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        List<Board> boards = boardRepository.getAllBoards(workspace.getId());
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boards) {
            boardResponses.add(
                    new BoardResponse(
                            board.getId(),
                            board.getName(),
                            board.getBackground(),
                            board.getFavourite()
                    ));
        }
        return boardResponses;
    }

    @Override
    public BoardResponse getById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getBackground(),
                board.getFavourite()
        );
    }

}
