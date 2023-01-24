package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest authRequest);

    AuthResponse registerAndAuthWithGoogle(String tokenFront) throws FirebaseAuthException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
