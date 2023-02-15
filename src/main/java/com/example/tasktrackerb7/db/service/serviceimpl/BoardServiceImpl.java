package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.BoardRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.db.service.BoardService;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.request.BoardUpdateRequest;
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
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public BoardResponse create(BoardRequest boardRequest) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(boardRequest.getWorkspaceId()).orElseThrow(() -> {
            throw new NotFoundException("workspace not found");
        });
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            Board board = new Board(boardRequest);
            workspace.addBoard(board);
            board.setWorkspace(workspace);
            boardRepository.save(board);
            boolean isFavourite = false;
            if (board.getFavourites() != null) {
                for (Favourite favorite : user.getFavourites()) {
                    if (board.getFavourites().contains(favorite)) {
                        isFavourite = true;
                        break;
                    }
                }
            }
            return new BoardResponse(board.getId(), board.getName(), board.getBackground(), isFavourite);

        } else throw new BadRequestException("you are not member in this workspace");

    }

    @Override
    public BoardResponse update(BoardUpdateRequest boardUpdateRequest) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(boardUpdateRequest.getBoardId()).orElseThrow(() -> {
            throw new NotFoundException("board not found");
        });
        Workspace workspace = workspaceRepository.findById(board.getWorkspace().getId()).orElseThrow(() -> {
            throw new NotFoundException("workspace not found");
        });
        if (!workspace.getBoards().contains(board)) {
            throw new NotFoundException("This board not found!");
        } else {
            if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

                if (!boardUpdateRequest.isBackground()) {
                    board.setName(boardUpdateRequest.getValue());
                }
                if (boardUpdateRequest.isBackground()) {
                    board.setBackground(boardUpdateRequest.getValue());
                }
                boolean isFavourite = false;
                if (board.getFavourites() != null) {
                    for (Favourite favorite : user.getFavourites()) {
                        if (board.getFavourites().contains(favorite)) {
                            isFavourite = true;
                            break;
                        }
                    }
                }
                boardRepository.save(board);
                return new BoardResponse(board.getId(), board.getName(), board.getBackground(), isFavourite);

            } else throw new BadRequestException("you are nor member");

        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("board not found");
        });
        Workspace workspace = board.getWorkspace();
        if (!workspace.getBoards().contains(board)) {
            throw new NotFoundException("we don't have this board in this workspace");
        } else {
            if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

                if (board.getFavourites() != null) {
                    for (Favourite f : board.getFavourites()) {
                        f.setBoard(null);
                    }
                }
                board.setFavourites(null);
                boardRepository.delete(board);
                return new SimpleResponse("board deleted with id: " + id + " successfully");

            } else throw new BadRequestException("you are not member in this workspace");

        }
    }

    @Override
    public List<BoardResponse> getAllByWorkspaceId(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("workspace not found");
        });
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {

            List<Board> boards = boardRepository.getAllBoards(workspace.getId());
            List<BoardResponse> boardResponses = new ArrayList<>();
            for (Board board : boards) {
                boolean isFavourite = false;
                if (board.getFavourites() != null) {
                    for (Favourite favorite : user.getFavourites()) {
                        if (board.getFavourites().contains(favorite)) {
                            isFavourite = true;
                            break;
                        }
                    }
                }
                boardResponses.add(new BoardResponse(board.getId(), board.getName(), board.getBackground(), isFavourite));
            }
            return boardResponses;

        } else throw new BadRequestException("you are not member in this workspace");

    }

    @Override
    public BoardResponse getById(Long id) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("board not found");
        });
        boolean isFavourite = false;
        if (board.getFavourites() != null) {
            for (Favourite favorite : user.getFavourites()) {
                if (board.getFavourites().contains(favorite)) {
                    isFavourite = true;
                    break;
                }
            }
        }
        return new BoardResponse(board.getId(), board.getName(), board.getBackground(), isFavourite);
    }
}



