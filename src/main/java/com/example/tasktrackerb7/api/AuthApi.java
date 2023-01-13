package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthApi {

    private final UserService userService;

    @PostMapping("/registration")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @PostMapping("/google")
    public AuthResponse registerAndAuthWithGoogle(String tokenId) throws FirebaseAuthException {
        return userService.registerAndAuthWithGoogle(tokenId);
    }
}
