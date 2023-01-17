package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authInfos")
@Getter
@Setter
@NoArgsConstructor
public class AuthInfo {

    @Id
    @SequenceGenerator(name = "authInfo_seq", sequenceName = "authInfo_seq", allocationSize = 1)
    @GeneratedValue(generator = "authInfo_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String password;

    public AuthInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
