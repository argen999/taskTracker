package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.MemberService;
import com.example.tasktrackerb7.dto.response.ParticipantResponse;
import com.example.tasktrackerb7.dto.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Member API", description = "Member API")
public class MemberAPI {

    private final MemberService memberService;

    @Operation(summary = "Delete member by ID from workspace", description = "Delete member by ID from workspace")
    @DeleteMapping("/{workspaceID}/{memberID}")
    SimpleResponse deleteMemberByIDFromWorkspace(@PathVariable Long workspaceID, @PathVariable Long memberID) {
        return memberService.deleteMemberByIDFromWorkspace(workspaceID, memberID);
    }

    @Operation(summary = "Get all members by workspaceID", description = "Get all members by workspaceID")
    @GetMapping("/getAllMembersByWorkspaceID/{workspaceID}")
    public List<ParticipantResponse> getAllParticipantsByWorkspaceID(@PathVariable Long workspaceID) {
        return memberService.getAllParticipantsByWorkspaceID(workspaceID);
    }

    @Operation(summary = "Change role", description = "Change role")
    @PutMapping("/{roleID}/{memberID}/{workspaceID}")
    public void changeMemberRole(@PathVariable Long roleID, @PathVariable Long memberID, @PathVariable Long workspaceID) {
        memberService.changeMemberRole(roleID, memberID, workspaceID);
    }
}
