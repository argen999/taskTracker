package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {

    private String name;
    private String surname;
    private String email;
    private Role role;
}
