package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Board;
import com.example.tasktrackerb7.db.entities.Favourite;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.FavouriteService;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final BoardRepository boardRepository;

    private final FavouriteRepository favouriteRepository;

    private final UserRepository userRepository;

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public SimpleResponse makeFavouriteBoard(Long id) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                });
        int count = 0;
        if (board.getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), board.getWorkspace().getId()))) {
            if (user.getFavourites() != null) {
                for (Favourite f : board.getFavourites()) {
                    if (Objects.equals(f.getBoard().getId(), id)) {
                        favouriteRepository.delete(f);
                        count++;
                        break;
                    }
                }

            }
            if (count < 1) {
               Favourite newFavourite = new com.example.tasktrackerb7.db.entities.Favourite();
               newFavourite.setIsBoard(false);
                newFavourite.setBoard(board);
                newFavourite.setUser(user);
                user.addFavourite(newFavourite);
                favouriteRepository.save(newFavourite);
            }
        } else {
            throw new NotFoundException("user not found this board");
        }
        return new SimpleResponse("make favourite  board  successfully!!");
    }

    @Override
    public SimpleResponse makeFavouriteWorkspace(Long id) {
        User user = getAuthenticateUser();
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("Workspace  not found");
                });
        int count = 0;
        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
            if (user.getFavourites() != null) {
                for (Favourite favourite : workspace.getFavourites()) {
                    if (Objects.equals(favourite.getWorkspace().getId(), id)) {
                        favouriteRepository.delete(favourite);
                        count++;
                        break;
                    }
                }
            }

            if (count < 1) {
                Favourite favourite = new com.example.tasktrackerb7.db.entities.Favourite();
                workspace.setBoards(null);
                favourite.setIsBoard(true);
                favourite.setWorkspace(workspace);
                favourite.setUser(user);
                user.addFavourite(favourite);
                favouriteRepository.save(favourite);

            }
        } else {
            throw new NotFoundException("user not found this workspace!!");
        }
        return new SimpleResponse("make favourite workspace successfully!!");
    }

    @Override
    public FavouriteResponse getAllFavourite() {
        User user = getAuthenticateUser();
        FavouriteResponse favourites = new FavouriteResponse();
        favourites.setBoard(favouriteRepository.getFavouriteByIsBoard(user.getId()));
        favourites.setWorkspace(favouriteRepository.getWorkspaceByIsFavourite(user.getId()));
        return favourites;
    }
}



