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
    @SequenceGenerator(name = "userWorkspaceRole_seq", sequenceName = "userWorkspaceRole_seq", allocationSize = 1)
    @GeneratedValue(generator = "userWorkspaceRole_seq", strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private User userId;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private Workspace workspaceId;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private Role roleId;
}
