package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.*;
import com.example.tasktrackerb7.db.repository.*;
import com.example.tasktrackerb7.db.service.CommentService;
import com.example.tasktrackerb7.dto.request.CommentRequest;
import com.example.tasktrackerb7.dto.response.CommentResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import com.example.tasktrackerb7.dto.response.UserResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final NotificationRepository notificationRepository;

    private final BoardRepository boardRepository;

    private final ColumnRepository columnRepository;

    private final WorkspaceRepository workspaceRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found!");
            throw new NotFoundException("User not found!");
        });
    }

    @Override
    public CommentResponse saveComment(CommentRequest commentRequest) {
        User user = getAuthenticateUser();

        Card card = cardRepository.findById(commentRequest.getId()).orElseThrow(
                () -> {
                    log.error("card not found");
                    throw new NotFoundException("card not found");
                }
        );

        Board board = boardRepository.findById(card.getColumn().getBoard().getId()).orElseThrow(() -> {
                    log.error("board not found!");
                    throw new NotFoundException("board not found!");
                }
        );
        Column column = columnRepository.findById(card.getColumn().getId()).orElseThrow(() -> {
                    log.error("column bot found!");
                    throw new NotFoundException("column bot found!");
                }
        );
        Comment comment = new Comment();

        comment.setUser(user);

        comment.setText(commentRequest.getText());
        comment.setLocalDateTime(LocalDateTime.now());
        comment.setCard(card);
        card.addComment(comment);

        commentRepository.save(comment);
        log.info("Comment successfully created");
        if (!userRepository.getAll(commentRequest.getId()).isEmpty()) {
            for (User u : userRepository.getAll(commentRequest.getId())) {

                Notification notification = new Notification();
                notification.setBoard(board);
                notification.setColumn(column);
                notification.setCard(card);
                notification.setDateOfWrite(LocalDate.now());
                notification.setText(commentRequest.getText());
                notification.setFromUser(user);
                notification.setStatus(false);

                u.addComment(comment);
                if (!u.equals(user)) {
                    u.addNotification(notification);
                    notification.setUser(u);
                    notificationRepository.save(notification);
                }

            }
        } else throw new BadRequestException("This list is null!");

        return new CommentResponse(comment.getId(), comment.getText(), comment.getLocalDateTime(), true, new UserResponse(getAuthenticateUser().getId(), getAuthenticateUser().getName() + " " + getAuthenticateUser().getSurname(), getAuthenticateUser().getPhotoLink()), comment.getUser());
    }

    @Override
    public CommentResponse editComment(CommentRequest commentRequest) {
        User user = getAuthenticateUser();
        Comment comment = commentRepository.findById(commentRequest.getId()).orElseThrow(
                () -> {
                    log.error("comment not found!!");
                    throw new NotFoundException("comment not found!!");
                }

        );
        if (!user.equals(comment.getUser())) {
            log.error("You can not edit this comment!");
            throw new BadCredentialsException("You cannot edit this comments!!");
        }
        comment.setText(commentRequest.getText());
        comment.setLocalDateTime(LocalDateTime.now());
        commentRepository.save(comment);
        log.info("Comment  successfully edited");

        return new CommentResponse(comment.getId(), comment.getText(), comment.getLocalDateTime(), true, new UserResponse(getAuthenticateUser().getId(), getAuthenticateUser().getName() + " " + getAuthenticateUser().getSurname(), getAuthenticateUser().getPhotoLink()), comment.getUser());
    }

    @Override
    public SimpleResponse deleteComment(Long id) {
        User user = getAuthenticateUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> {
                    log.error("comment not found");
                    throw new NotFoundException("comment not found");
                }
        );
        if (!user.equals(comment.getUser())) {
            log.error("You can't delete this comment");
            throw new BadCredentialsException("You can't delete this comment");
        } else {
            commentRepository.delete(comment);
            log.info("Comment successfully deleted!");
        }
        return new SimpleResponse("Comment delete successfully!!!");
    }

    @Override
    public List<CommentResponse> getAllComments(Long id) {
        List<Comment> comments = commentRepository.getAllComments(id);
        List<CommentResponse> commentResponses = comments.stream().map(c -> new
                CommentResponse(c.getId(), c.getText(), c.getLocalDateTime(), true,
                new UserResponse(c.getUser().getId(), c.getUser().getName() + " " +
                        c.getUser().getSurname(), c.getUser().getPhotoLink()), c.getUser())).toList();
        for (CommentResponse c : commentResponses) {
            if (c.getUser() == getAuthenticateUser()) {
                c.setIsMine(true);
            } else {
                c.setIsMine(false);
            }
        }
        return commentResponses;
    }
}
