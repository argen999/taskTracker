package com.example.tasktrackerb7.api;


import com.example.tasktrackerb7.db.service.MemberService;
import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.request.InvitationRequest;
import com.example.tasktrackerb7.dto.response.MemberResponse;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Member API", description = "Member API")
public class MemberApi {

    private final MemberService memberService;

    private final UserService userService;

    @Operation(summary = "Invite member to workspace", description = "Invite member to workspace")
    @PostMapping("/inviteMemberToWorkspace")
    public SimpleResponse inviteMemberToWorkspace(@RequestBody InvitationRequest invitationRequest) throws MessagingException {
        return memberService.inviteMemberToWorkspace(invitationRequest);
    }

    @Operation(summary = "Change member role", description = "Change member role")
    @PutMapping("/{roleId}/{memberId}/{workspaceId}")
    public void changeMemberRole(@PathVariable Long roleId, @PathVariable Long memberId, @PathVariable Long workspaceId) {
        memberService.changeMemberRole(roleId, memberId, workspaceId);
    }

    @Operation(summary = "Delete member by ID from workspace", description = "Delete member by ID from workspace")
    @DeleteMapping("/{workspaceId}/{memberId}")
    public SimpleResponse deleteMemberByIdFromWorkspace(@PathVariable Long workspaceId, @PathVariable Long memberId) {
        return memberService.deleteMemberByIdFromWorkspace(workspaceId, memberId);
    }

    @Operation(summary = "Get all participants by workspace ID", description = "Get all participants by workspace ID")
    @GetMapping("/getAllParticipantsByWorkspaceId/{workspaceId}")
    public List<ParticipantResponse> getAllParticipantsByWorkspaceId(@PathVariable Long workspaceId) {
        return memberService.getAllParticipantsByWorkspaceId(workspaceId);

    }

    @Operation(summary = "Get all admins by workspace ID", description = "Get all admins by workspace ID")
    @GetMapping("/getAllAdminsByWorkspaceId/{workspaceId}")
    public List<ParticipantResponse> getAllAdminsByWorkspaceId(@PathVariable Long workspaceId) {
        return memberService.getAllAdminsByWorkspaceId(workspaceId);

    }

    @Operation(summary = "Get all members by workspace ID", description = "Get all members by workspace ID")
    @GetMapping("/getAllMembersByWorkspaceId/{workspaceId}")
    public List<ParticipantResponse> getAllMembersByWorkspaceId(@PathVariable Long workspaceId) {
        return memberService.getAllMembersByWorkspaceId(workspaceId);

    }

    @GetMapping("/search/{id}")
    @Operation(summary = "Search users", description = "Search users by name, surname and email in this workspace")
    public List<MemberResponse> search(@PathVariable Long id, @RequestParam String email) {
        return userService.search(id, email);
    }

}
