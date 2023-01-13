package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Role role;

    private String jwt;

    public AuthResponse(Long id, String name, String surname, String email, Role role, String jwt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.jwt = jwt;
    }

}
