package com.example.tasktrackerb7.dto.response;

import com.example.tasktrackerb7.db.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Role role;

    public RegisterResponse(Long id, String name, String surname, String email, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}
