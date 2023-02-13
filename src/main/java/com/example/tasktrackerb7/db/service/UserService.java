package com.example.tasktrackerb7.db.service;

import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.ProfileRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.*;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest authRequest);

    AuthResponse registerAndAuthWithGoogle(String tokenFront) throws FirebaseAuthException;

    ProfileResponse updatingUserData(ProfileRequest profileRequest);

    List<WorkspaceResponse> getAllWorkspaceOwnedByUser();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<MemberResponse> search(Long id, String email_name);
}
