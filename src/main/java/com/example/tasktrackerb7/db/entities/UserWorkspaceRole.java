package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "userWorkspaceRoles")
@Getter
@Setter
@NoArgsConstructor
public class UserWorkspaceRole {

    @Id
    @SequenceGenerator(name = "userWorkspaceRole_gen", sequenceName = "userWorkspaceRole_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "userWorkspaceRole_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Workspace workspace;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Role role;

    public UserWorkspaceRole(User user, Workspace workspace, String role) {

    }
}
