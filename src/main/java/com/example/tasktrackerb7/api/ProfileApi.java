package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.ProfileRequest;
import com.example.tasktrackerb7.dto.request.ResetPasswordRequest;
import com.example.tasktrackerb7.dto.response.*;
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
@RequestMapping("api/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Profile Api", description = "Update and get by id profile")
public class ProfileApi {

    private final UserService userService;

    @Operation(summary = "Get authenticated profile", description = "Get authenticated profile")
    @GetMapping
    public ProfileInnerPageResponse getMyProfile() {
        return userService.getMyProfile();
    }

    @Operation(summary = "Get profile", description = "Get profile by id")
    @GetMapping("/{id}")
    public ProfileInnerPageResponse getProfileById(@PathVariable Long id) {
        return userService.getProfileById(id);
    }

    @Operation(summary = "Update", description = "Update user data")
    @PutMapping
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
    @PostMapping("/forgot/password")
    public SimpleResponse forgotPassword(@RequestParam String email,
                                         @RequestParam String link)throws MessagingException {
        return userService.forgotPassword(email,link);
    }

    @Operation(summary = "Reset password", description = "Allows you to reset the user's password")
    @PutMapping("/reset/password")
    public ResetPasswordResponse resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return userService.resetPassword(request);
    }
}
