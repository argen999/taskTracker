package com.example.tasktrackerb7.db.service;


import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest authRequest);

    AuthResponse registerAndAuthWithGoogle(String tokenFront) throws FirebaseAuthException;
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
