package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Board;
import com.example.tasktrackerb7.db.entities.Favourite;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.FavouriteService;
import com.example.tasktrackerb7.dto.request.FavouriteRequest;
import com.example.tasktrackerb7.dto.response.FavouriteResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


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
    public FavouriteResponse makaFavouriteBoard(Long id, FavouriteRequest favouriteRequest) {
        User user = getAuthenticateUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("board not found");
                });

        Favourite favourite = new Favourite();
        if (board.getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), board.getWorkspace().getId()))) {
            if (user.getWorkspaces().contains(board.getWorkspace())) {
                if (favouriteRequest.isFavourite()) {
                    favourite.setBoard(board);
                    board.addFavourite(favourite);
                    favourite.setUser(user);
                    user.addFavourite(favourite);
                    favouriteRepository.save(favourite);

                }else {
                    throw new BadRequestException("board true");
                }
            }else{
                throw new BadCredentialsException("this board not is workspace");
            }
        }

        return new FavouriteResponse(true);
    }
}


//        @Override
//        public SimpleResponse deleteBoardIsFavourite (Long id){
//        User user = getAuthenticateUser();
//        Favourite favourite = favouriteRepository.findById(id).orElseThrow(
//                () -> {
//                    throw new NotFoundException("favourite not found");
//                });
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> {
//                    throw new NotFoundException("board not found");
//                });
//        if (board.getWorkspace().getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), board.getWorkspace().getId()))) {
//            if (!board.getFavourites().contains(favourite)) {
//                throw new NotFoundException("board" + id + "not found this favourites");
//            } else {
//                if (board.getFavourites() != null) {
//                    favourite.setBoard(null);
//                }
//                favouriteRepository.delete(favourite);
//            }
//        } else {
//            throw new BadCredentialsException("you can't delete this board id"+" " + id);
//        }
//            return new SimpleResponse("delete board successfully");
//        }

//    @Override
//    public FavouriteResponse makeFavouriteWorkspace(FavouriteRequest favouriteRequest, Long id) {
//        User user = getAuthenticateUser();
//        Workspace workspace = workspaceRepository.findById(id).orElseThrow(
//                () -> {
//                    throw new NotFoundException("workspace not found");
//                });
//        Favourite favourite = new Favourite();
//        if (workspace.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), workspace.getId()))) {
//            for (Favourite f : user.getFavourites()) {
//                if (!f.getWorkspace().equals(workspace)) {
//                    if (favouriteRequest.isFavourite()) {
//                        throw new BadRequestException("This is favourite is exists! ");
//                    } else {
//                        favourite.setWorkspace(workspace);
//                        workspace.addFavourite(favourite);
//                        favourite.setUser(user);
//                        user.addFavourite(favourite);
//                    }
//                    favouriteRepository.save(favourite);
//                } else {
//                    throw new BadCredentialsException("This is favourite is exists workspace! ");
//                }
//
//            }
//        } else {
//            throw new BadRequestException("User this favourite not found");
//        }
//        return new FavouriteResponse(favourite.getId(), favourite.getWorkspace());
//    }


//        @Override
//        public SimpleResponse deleteWorkspaceIsFavourite (Long id, Long workspaceId){
//        User user = getAuthenticateUser();
//        Favourite favourite = favouriteRepository.findById(id).orElseThrow(
//                () -> {
//                    throw new NotFoundException("favourite not found");
//                });
//        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
//                () -> {
//                    throw new NotFoundException("workspace not found");
//                });
//        if (!workspace.getFavourites().contains(favourite)) {
//            throw new NotFoundException("workspace" + workspaceId + "not found this favourites");
//        } else {
//            if (workspace.getFavourites() != null) {
//                favourite.setWorkspace(null);
//
//            }
//            favouriteRepository.delete(favourite);
//        }
//            return new SimpleResponse("delete workspace successfully");
//







