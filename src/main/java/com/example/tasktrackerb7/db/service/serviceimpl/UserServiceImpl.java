package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.configs.jwt.JwtTokenUtil;
import com.example.tasktrackerb7.db.entities.AuthInfo;
import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.AuthInfoRepository;
import com.example.tasktrackerb7.db.repository.RoleRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthInfoRepository authInfoRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException(String.format("a user with this email %s already exists", request.getEmail()));
        } else {
            AuthInfo authInfo = new AuthInfo();
            authInfo.setEmail(request.getEmail());
            authInfo.setPassword(passwordEncoder.encode(request.getPassword()));
            authInfoRepository.save(authInfo);

            Role role = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("not found role"));

            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setAuthInfo(authInfo);
            user.setRoles(Collections.singletonList(role));

            userRepository.save(user);

            String jwt = jwtTokenUtil.generateToken(user.getAuthInfo().getEmail());
            return new AuthResponse(user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getAuthInfo().getEmail(),
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
        String jwt = jwtTokenUtil.generateToken(user.getAuthInfo().getEmail());
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getAuthInfo().getEmail(),
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
    public AuthResponse registerAndAuthWithGoogle(String tokenFront) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenFront);

        Role role = roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Not found!"));

        if (!userRepository.existsByEmail(tokenFront)) {

            String[] fullName = tokenFront.split(" ");

            User newUser = new User();
            newUser.setName(fullName[0]);
            newUser.setSurname(fullName[1]);
            newUser.setAuthInfo(new AuthInfo(firebaseToken.getEmail(), firebaseToken.getEmail()));
            newUser.addRole(role);

            userRepository.save(newUser);
        }

        User user = userRepository.findByEmail(firebaseToken.getEmail())
                .orElseThrow(() -> new NotFoundException(String.format("User with %s not found!",firebaseToken.getEmail())));

        String token = jwtTokenUtil.generateToken(user.getAuthInfo().getEmail());
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                role.getName(),
                token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format("!%s not found!", username)));
    }
}
