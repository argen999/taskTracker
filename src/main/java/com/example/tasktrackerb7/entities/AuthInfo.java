package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
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
    @SequenceGenerator(name = "authInfo_seq",sequenceName = "authInfo_seq",allocationSize = 1)
    @GeneratedValue(generator = "authInfo_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String password;
}
