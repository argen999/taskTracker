package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.configs.jwt.JwtTokenUtil;
import com.example.tasktrackerb7.db.entities.Role;
import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.repository.RoleRepository;
import com.example.tasktrackerb7.db.repository.UserRepository;
import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.example.tasktrackerb7.exceptions.BadCredentialsException;
import com.example.tasktrackerb7.exceptions.BadRequestException;
import com.example.tasktrackerb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtTokenUtil;


    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException(String.format("a user with this email %s already exists", request.getEmail()));
        }else {
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
                    role,
                    jwt);
        }
    }

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
                roleRepository.findById(2L).orElseThrow( () -> new NotFoundException("role cannot be send to response")),
                jwt);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("!email not found!"));
    }
}
