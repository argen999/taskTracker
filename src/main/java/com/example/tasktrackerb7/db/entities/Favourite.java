package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
public class Favourite {

    @Id
    @SequenceGenerator(name = "favourite_gen", sequenceName = "favourite_seq", allocationSize = 1)
    @GeneratedValue(generator = "favourite_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    @JoinTable(name = "favourite_workspace",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "workspace_id"))
    private List<Workspace> workspaces;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    @JoinTable(name = "favourite_board",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id"))
    private List<Board> boards;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    @JoinTable(name = "favourite_user",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
