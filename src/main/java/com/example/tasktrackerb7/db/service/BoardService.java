package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.db.entities.Board;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.BoardRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.dto.request.BoardRequest;
import com.example.tasktrackerb7.dto.response.BoardResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final WorkspaceRepository workspaceRepository;


    public BoardResponse create(BoardRequest boardRequest) {
        Workspace workspace = workspaceRepository.findById(boardRequest.getWorkspaceId()).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        Board board = new Board(boardRequest);
        if (boardRepository.existsByName(boardRequest.getName())) {
            throw new BadCredentialsException(String.format("a board with this name %s already exists", boardRequest.getName()));
        }
        workspace.addBoard(board);
        board.setWorkspace(workspace);
        boardRepository.save(board);
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getBackground(),
                board.getFavourite()
        );
    }


    public BoardResponse updateName(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
        if (boardRepository.existsByName(boardRequest.getName())) {
            throw new BadCredentialsException(String.format("a board with this name %s already exists", boardRequest.getName()));
        }
        board.setName(boardRequest.getName());
        boardRepository.save(board);
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getBackground(),
                board.getFavourite()
        );
    }

    public BoardResponse updateBackground(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
        board.setBackground(boardRequest.getBackground());
        boardRepository.save(board);
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getBackground(),
                board.getFavourite()
        );
    }

    public SimpleResponse delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                }
        );
            boardRepository.delete(board);
        return new SimpleResponse("board deleted successfully");
    }

    public List<BoardResponse> getAllByWorkspaceId(Long id) {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("workspace not found");
                }
        );
        List<Board> boards =  boardRepository.getAllBoards(workspace.getId());
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
