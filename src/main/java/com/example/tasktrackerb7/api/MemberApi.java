package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.db.service.UserService;
import com.example.tasktrackerb7.dto.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Member Api", description = "Operations related to users")
public class MemberApi {

    private final UserService userService;

    @GetMapping("/search/{id}")
    @Operation(summary = "Search users", description = "Search users by name, surname and email in this workspace")
    public List<MemberResponse> search(@PathVariable Long id, @RequestParam String email) {
        return userService.search(id, email);
    }
}
