package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    @SequenceGenerator(name = "workspace_gen", sequenceName = "workspace_seq",allocationSize = 1)
    @GeneratedValue(generator = "workspace_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workspace")
    private List<UserWorkspaceRole> members;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "workspace")
    private List<Board> boards;

    private boolean archive;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User creator;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH}, mappedBy = "workspace")
    private List<Favourite> favourites;

    public void addBoard(Board board) {
        if(board == null) {
            boards = new ArrayList<>();
        }
        boards.add(board);
    }
}

