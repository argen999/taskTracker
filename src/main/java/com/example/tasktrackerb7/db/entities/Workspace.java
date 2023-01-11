package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;

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

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workSpace_id")
    private List<UserWorkspaceRole> members;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workspace")
    private List<Board> boards;

    private boolean archive;

    private User creator;

    private Favourite favourite;

}

