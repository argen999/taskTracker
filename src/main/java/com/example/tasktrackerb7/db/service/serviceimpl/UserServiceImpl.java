package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.configs.jwt.JwtTokenUtil;
import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.Workspace;
import com.example.tasktrackerb7.db.repository.RoleRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.repository.UserWorkspaceRoleRepository;
import com.example.tasktrackerb7.db.repository.WorkspaceRepository;
import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.ProfileRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.example.tasktrackerb7.dto.response.AuthWithGoogleResponse;
import com.example.tasktrackerb7.dto.response.MemberResponse;
import com.example.tasktrackerb7.dto.response.ProfileResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.ExceptionResponse;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserWorkspaceRoleRepository userWorkspaceRoleRepository;

    private final WorkspaceRepository workspaceRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException(String.format("a user with this email %s already exists", request.getEmail()));
        } else {

            Role role = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("not found role"));

            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Collections.singletonList(role));

            userRepository.save(user);

            String jwt = jwtTokenUtil.generateToken(user.getEmail());
            return new AuthResponse(user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    role.getName(),
                    jwt);
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        if (authRequest.getPassword().isBlank()) {
            throw new BadRequestException("Password cannot be empty");
        }
        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("the user with this email %s was not found", authRequest.getEmail()));
                });
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        String jwt = jwtTokenUtil.generateToken(user.getEmail());
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("role cannot be send to response")).getName(),
                jwt);
    }

    @PostConstruct
    public void initConnectFireBase() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("task_tracker.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials).build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    @Override
    public AuthWithGoogleResponse registerAndAuthWithGoogle(String tokenId) throws ExceptionResponse {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        } catch (FirebaseAuthException firebaseAuthException) {
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, firebaseAuthException.getClass().getSimpleName(), firebaseAuthException.getMessage());
        }

        User user;
        Role role = roleRepository.findById(2L).get();
        if (userRepository.findByEmail(firebaseToken.getEmail()).isEmpty()) {
            user = new User();

            role.addUser(user);
            user.addRole(role);
            user.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());

            roleRepository.save(role);
        }

        user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        String token = jwtTokenUtil.generateToken(user.getEmail());
        return new AuthWithGoogleResponse(
                user.getId(),
                user.getEmail(),
                role.getName(),
                token);
    }

    @Override
    public ProfileResponse updatingUserData(ProfileRequest profileRequest) {
        User user = getAuthenticateUser();

        String email = user.getEmail();

        for (User u : userRepository.findAll()) {
            if (u.getEmail().equals(profileRequest.getEmail()) && !profileRequest.getEmail().equals(user.getEmail())) {
                throw new BadRequestException(String.format("This %s already exists!", profileRequest.getEmail()));
            }
        }

        user.setName(profileRequest.getName());
        user.setSurname(profileRequest.getSurname());
        user.setEmail(profileRequest.getEmail());
        user.setPassword(passwordEncoder.encode(profileRequest.getPassword()));
        user.setPhotoLink(profileRequest.getPhotoLink());

        userRepository.save(user);

        if (!email.equals(profileRequest.getEmail())) {
            return new ProfileResponse(user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    jwtTokenUtil.generateToken(user.getEmail()),
                    user.getPhotoLink());
        } else {
            return new ProfileResponse(user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getPhotoLink());
        }

    }

    @Override
    public List<WorkspaceResponse> getAllWorkspaceOwnedByUser() {
        User user = getAuthenticateUser();

        List<Workspace> userWorkspaces = user.getWorkspaces();

        for (Workspace w : workspaceRepository.findAll()) {
            if (w.getMembers().contains(userWorkspaceRoleRepository.findByUserIdAndWorkspaceId(user.getId(), w.getId()))
                    && !w.getCreator().equals(user)) {
                userWorkspaces.add(w);
            }
        }

        return userWorkspaces.stream().map(x -> new WorkspaceResponse(x.getId(),
                        x.getName(),
                        x.getCreator().getPhotoLink(),
                        x.getCreator().getName()))
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format("!%s not found!", username)));
    }

    @Override
    public List<MemberResponse> search(Long id, String email_name) {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workspace with id: " + id + " nont found"));
        return userRepository.searchByEmailOrName(email_name, workspace.getId());
    }
}
