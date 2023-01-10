package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@NoArgsConstructor
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspace_seq")
    @SequenceGenerator(name = "workspace_seq", sequenceName = "workspace_seq", allocationSize = 1)
    private Long workSpace_id;

    private String name;

    private boolean isFavourite;

   @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY,mappedBy = "workSpace_id")
   private List<UserWorkspaceRole> members;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY, mappedBy = "workspace")
    private List<Board> boards;

    private boolean archive;

    private User creator;

    private Favourite favourite;

}

