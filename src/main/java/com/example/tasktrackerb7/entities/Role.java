package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private Long role_id;
    private String name;
    @ManyToMany(targetEntity = User.class, mappedBy = "roles",
            cascade = {DETACH, MERGE, REFRESH})
    private List<User> users;
    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY,mappedBy = "role_id")
    private List<User_WorkSpace_Role>user_workSpace_roles;
}
