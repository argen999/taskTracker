package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.AuthRequest;
import com.example.tasktrackerb7.dto.request.ProfileRequest;
import com.example.tasktrackerb7.dto.request.RegisterRequest;
import com.example.tasktrackerb7.dto.request.ResetPasswordRequest;
import com.example.tasktrackerb7.dto.response.*;
import com.example.tasktrackerb7.dto.response.AuthResponse;
import com.example.tasktrackerb7.dto.response.AuthWithGoogleResponse;
import com.example.tasktrackerb7.dto.response.ProfileResponse;
import com.example.tasktrackerb7.dto.response.WorkspaceResponse;
import com.example.tasktrackerb7.exceptions.ExceptionResponse;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth Api", description = "Authentication and Authorization")
public class AuthApi {

    private final UserService userService;

    @Operation(summary = "Sign up", description = "Any user can register")
    @PostMapping("/registration")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
    }

    @Operation(summary = "Sign in", description = "Only registered users can login")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @Operation(summary = "Auth with google", description = "Any user can authenticate or register with google")
    @PostMapping("/")
    public AuthWithGoogleResponse authWithGoogle(String tokenId) throws FirebaseAuthException, ExceptionResponse {
        return userService.registerAndAuthWithGoogle(tokenId);
    }

    @Operation(summary = "Update", description = "Updating user data")
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ProfileResponse updatingUserData(@RequestBody @Valid ProfileRequest profileRequest) {
        return userService.updatingUserData(profileRequest);
    }

    @Operation(summary = "Get all", description = "Get all workspace owned by user")
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public List<WorkspaceResponse> getAllWorkspaceOwnedByUser() {
        return userService.getAllWorkspaceOwnedByUser();
    }

    @Operation(summary = "Forgot password", description = "If the user has forgotten the password")
    @PostMapping("forgot/password")
    public SimpleResponse forgotPassword(@RequestParam String email,
                                         @RequestParam String link)throws MessagingException{
        return userService.forgotPassword(email,link);
    }

    @Operation(summary = "Reset password", description = "Allows you to reset the user's password")
    @PutMapping("reset/password")
    public ResetPasswordResponse resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return userService.resetPassword(request);
    }

}
