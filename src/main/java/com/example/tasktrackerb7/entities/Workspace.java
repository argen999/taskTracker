package com.example.tasktrackerb7.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@NoArgsConstructor
public class Workspace {

    @Id
    @SequenceGenerator(name = "workspace_seq", sequenceName = "workspace_seq", allocationSize = 1)
    @GeneratedValue(generator = "workspace_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private boolean isFavourite;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workspaceId")
    private List<UserWorkspaceRole> members;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workspace")
    private List<Board> boards;

    private boolean archive;

    @Transient
    private User creator;

    @Transient
    private Favourite favourite;

}

